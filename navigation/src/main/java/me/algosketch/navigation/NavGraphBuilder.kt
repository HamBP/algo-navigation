package me.algosketch.navigation

import androidx.compose.runtime.Composable

fun NavGraphBuilder.composable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit,
) {
    addDestination(
        ComposeNavigator.Destination(content).apply {
            this.route = route
            arguments.forEach { (argumentName, argument) ->
                addArgument(argumentName, argument)
            }
            deepLinks.forEach { deepLink ->
                addDeepLink(deepLink)
            }
        }
    )
}

fun NavGraphBuilder.navigation(
    route: String,
    startDestination: String,
    arguments: List<NamedNavArgument>,
    deepLinks: List<NavDeepLink> = emptyList(),
    builder: NavGraphBuilder.() -> Unit
) {
    addDestination(
        NavGraphBuilder(startDestination).apply(builder).build().apply {
            this.route = route
            arguments.forEach { (argumentName, argument) ->
                addArgument(argumentName, argument)
            }
            deepLinks.forEach { deepLink ->
                addDeepLink(deepLink)
            }
        }
    )
}

open class NavGraphBuilder(
    private val startDestination: String,
) {
    private val destinations = mutableListOf<NavDestination>()

    fun addDestination(destination: NavDestination) {
        destinations += destination
    }

    fun build(): NavGraph {
        return NavGraph().apply {
            addDestinations(destinations)
            startDestinationRoute = startDestination
        }
    }
}
