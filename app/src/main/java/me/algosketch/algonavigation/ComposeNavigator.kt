package me.algosketch.algonavigation

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ComposeNavigator {
    private val _backStack: MutableStateFlow<List<NavBackStackEntry>> = MutableStateFlow(listOf())
    val backStack: StateFlow<List<NavBackStackEntry>> = _backStack.asStateFlow()

    fun initStartDestination(content: @Composable () -> Unit) {
        _backStack.update {
            it + NavBackStackEntry(Destination(content))
        }
    }

    class Destination(
        val content: @Composable () -> Unit
    )
}
