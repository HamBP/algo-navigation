package me.algosketch.navigation

import android.net.Uri
import android.os.Bundle
import androidx.annotation.MainThread
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.net.toUri
import me.algosketch.navigation.NavDestination.Companion.createRoute

@Composable
fun rememberNavController(): NavHostController {
    return remember {
        createNavController()
    }
}

private fun createNavController() = NavHostController().apply {
    addNavigator(ComposeNavigator())
    addNavigator(ComposeNavGraphNavigator(navigatorProvider))
}

open class NavHostController {
    val navigatorProvider = mutableMapOf<String, Navigator<out NavDestination>>()
    private val backQueue: ArrayDeque<NavBackStackEntry> = ArrayDeque()
    private val navigatorState =
        mutableMapOf<Navigator<out NavDestination>, NavControllerNavigatorState>()
    private var addToBackStackHandler: ((backStackEntry: NavBackStackEntry) -> Unit)? = null

    private var _graph: NavGraph? = null
    var graph: NavGraph
        @MainThread
        get() {
            checkNotNull(_graph) { "NavGraph가 초기화되지 않았어요" }
            return _graph as NavGraph
        }
        @MainThread
        set(value) {
            if (value != _graph) {
                _graph = value
                onGraphCreated()
                navigate(value, null)
            }
        }

    private fun onGraphCreated() {
        navigatorProvider.values.filterNot { it.isAttached }.forEach { navigator ->
            val navigatorBackStack = navigatorState.getOrPut(navigator) {
                NavControllerNavigatorState(navigator)
            }
            navigator.onAttach(navigatorBackStack)
        }
    }

    inline fun createGraph(builder: NavGraphBuilder): NavGraph {
        return builder.build()
    }

    fun navigate(route: String) {
        navigate(NavDeepLinkRequest(createRoute(route).toUri()))
    }

    fun navigate(deepLink: Uri) {
        navigate(NavDeepLinkRequest(deepLink))
    }

    fun navigate(request: NavDeepLinkRequest) {
        val deepLinkMatch = graph.matchDeepLink(request)
        if (deepLinkMatch != null) {
            val args = deepLinkMatch.matchingArgs ?: Bundle()
            val node = deepLinkMatch.destination
            navigate(node, args)
        } else {
            throw IllegalArgumentException(
                "Navigation destination that matches request $request cannot be found in the " +
                        "navigation graph $_graph"
            )
        }
    }

    private fun navigate(
        node: NavDestination,
        args: Bundle?,
    ) {
        val backStackEntry = NavBackStackEntry(node, args)
        val navigator = navigatorProvider[node.navigatorName]!!

        navigator.navigateInternal(backStackEntry) {
            addEntryToBackStack(node, args, it)
        }
    }

    private fun Navigator<out NavDestination>.navigateInternal(
        entry: NavBackStackEntry,
        handler: (backStackEntry: NavBackStackEntry) -> Unit = {}
    ) {
        addToBackStackHandler = handler
        navigate(entry)
        addToBackStackHandler = null
    }

    fun addNavigator(navigator: Navigator<out NavDestination>) {
        navigatorProvider[navigator.name] = navigator
    }

    fun popBackStack(): Boolean {
        return if (backQueue.isEmpty()) {
            false
        } else {
            popBackStack(backQueue.last().destination.route!!, true)
        }
    }

    fun popBackStack(route: String, inclusive: Boolean): Boolean {
        val popped = popBackStackInternal(route, inclusive)

        if (backQueue.isNotEmpty() && backQueue.last().destination is NavGraph) {
            popEntryFromBackStack()
        }

        return popped
    }

    private fun popBackStackInternal(route: String, inclusive: Boolean): Boolean {
        val popOperations = mutableListOf<Navigator<*>>()
        val iterator = backQueue.reversed().iterator()
        while (iterator.hasNext()) {
            val destination = iterator.next().destination
            val navigator = navigatorProvider[destination.navigatorName]!!

            if (inclusive || route == destination.route) {
                popOperations.add(navigator)
            }
            if (destination.route == route) {
                break
            }
        }
        return executePopOperations(popOperations)
    }

    private fun executePopOperations(
        popOperations: List<Navigator<*>>,
    ): Boolean {
        var poped = false

        for (navigator in popOperations) {
            navigator.popBackStack(backQueue.last())
            poped = true
        }
        return poped
    }

    private fun popEntryFromBackStack() {
        val entry = backQueue.last()
        backQueue.removeLast()
    }

    private fun addEntryToBackStack(
        node: NavDestination,
        finalArgs: Bundle?,
        backStackEntry: NavBackStackEntry,
    ) {
        val hierarchy = ArrayDeque<NavBackStackEntry>()
        var destination: NavDestination? = backStackEntry.destination
        if (node is NavGraph) {
            do {
                val parent = destination!!.parent
                if (parent != null) {
                    val entry = NavBackStackEntry(parent, finalArgs)
                    hierarchy.addFirst(entry)
                }
                destination = parent
            } while (destination != null && destination != node)
        }

        hierarchy.forEach { entry ->
            val navigator = navigatorProvider[entry.destination.navigatorName]!!
            val navigatorBackStack = checkNotNull(navigatorState[navigator]) {
                "NavigatorBackStack for ${node.navigatorName} should already be created"
            }
            navigatorBackStack.addInternal(entry)
        }

        backQueue.addAll(hierarchy)
        backQueue.add(backStackEntry)
    }

    fun getBackStackEntry(route: String): NavBackStackEntry {
        val backStack = backQueue.lastOrNull { entry ->
            entry.destination.hasRoute(route)
        }

        requireNotNull(backStack) {
            "현재 백스택에서 ${route}를 찾을 수 없어요."
        }

        return backStack
    }

    private inner class NavControllerNavigatorState(
        val navigator: Navigator<out NavDestination>
    ) : NavigatorState() {
        override fun push(backStackEntry: NavBackStackEntry) {
            addToBackStackHandler?.invoke(backStackEntry)
            addInternal(backStackEntry)
        }

        fun addInternal(backStackEntry: NavBackStackEntry) {
            super.push(backStackEntry)
        }
    }
}
