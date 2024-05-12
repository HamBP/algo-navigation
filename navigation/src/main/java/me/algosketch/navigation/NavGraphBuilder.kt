package me.algosketch.navigation

import androidx.compose.runtime.Composable

fun NavGraphBuilder.composable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit,
) {
    addDestination(
        ComposeNavigator.Destination(content).apply {
            this.route = route
            arguments.forEach { (argumentName, argument) ->
                addArgument(argumentName, argument)
            }
        }
    )
}

fun NavGraphBuilder.navigation(
    route: String,
    startDestination: String,
    arguments: List<NamedNavArgument>,
    builder: NavGraphBuilder.() -> Unit
) {
    addDestination(
        NavGraphBuilder(startDestination).apply(builder).build().apply {
            this.route = route
            arguments.forEach { (argumentName, argument) ->
                addArgument(argumentName, argument)
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
