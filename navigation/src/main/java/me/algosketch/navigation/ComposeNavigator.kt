package me.algosketch.navigation

import androidx.compose.runtime.Composable

class ComposeNavigator : Navigator<ComposeNavigator.Destination>("composable") {
    class Destination(
        val content: @Composable () -> Unit
    ) : NavDestination("composable") {
        private var _arguments: MutableMap<String, NamedNavArgument> = mutableMapOf()
        val arguments = _arguments.toMap()

        fun addArgument(argumentName: String, argument: NamedNavArgument) {
            _arguments[argumentName] = argument
        }
    }

    companion object {
        const val NAME = "composable"
    }
}
