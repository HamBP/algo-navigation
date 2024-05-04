package me.algosketch.algonavigation

import androidx.compose.runtime.Composable

fun NavGraphBuilder.composable(
    route: String,
    content: @Composable () -> Unit,
) {
    addDestination(
        ComposeNavigator.Destination(content).apply {
            this.route = route
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
