package me.algosketch.algonavigation

import androidx.compose.runtime.Composable

fun NavGraphBuilder.composable(
    content: @Composable () -> Unit,
) {
    addDestination(
        ComposeNavigator.Destination(content)
    )
}

open class NavGraphBuilder {
    private val destinations = mutableListOf<ComposeNavigator.Destination>()

    fun addDestination(destination: ComposeNavigator.Destination) {
        destinations += destination
    }

    fun build(): List<ComposeNavigator.Destination> {
        return destinations.toList()
    }
}
