package me.algosketch.navigation

import androidx.compose.runtime.Composable

class ComposeNavigator : Navigator<ComposeNavigator.Destination>("composable") {
    val backStack get() = state.backStack

    class Destination(
        val content: @Composable (NavBackStackEntry) -> Unit
    ) : NavDestination("composable")

    companion object {
        const val NAME = "composable"
    }
}
