package me.algosketch.navigation

import androidx.compose.runtime.Composable

class ComposeNavigator : Navigator<ComposeNavigator.Destination>("composable") {
    class Destination(
        val content: @Composable () -> Unit
    ) : NavDestination("composable")

    companion object {
        const val NAME = "composable"
    }
}
