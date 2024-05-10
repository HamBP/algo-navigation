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

open class NavGraphBuilder(
    private val startDestination: String,
) {
    private val destinations = mutableListOf<ComposeNavigator.Destination>()

    fun addDestination(destination: ComposeNavigator.Destination) {
        destinations += destination
    }

    fun build(): NavGraph {
        return NavGraph().apply {
            nodes += destinations.toList()
            startDestinationRoute = startDestination
        }
    }
}
