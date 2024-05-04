package me.algosketch.navigation

import androidx.annotation.MainThread
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

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
                navigate(value)
            }
        }

    inline fun createGraph(builder: NavGraphBuilder): NavGraph {
        return builder.build()
    }

    fun navigate(route: String) {
        val node = graph.nodes.find { it.route == route }!!
        navigate(node)
    }

    private fun navigate(
        node: NavDestination,
    ) {
        val backStackEntry = NavBackStackEntry(node)
        val navigator = navigatorProvider[node.navigatorName]!!
        navigator.navigate(backStackEntry)
    }

    fun addNavigator(navigator: Navigator<out NavDestination>) {
        navigatorProvider[navigator.name] = navigator
    }
}