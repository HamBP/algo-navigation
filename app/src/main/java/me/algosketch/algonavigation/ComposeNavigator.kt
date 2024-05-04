package me.algosketch.algonavigation

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ComposeNavigator {
    private val _backStack: MutableStateFlow<List<NavBackStackEntry>> = MutableStateFlow(listOf())
    val backStack: StateFlow<List<NavBackStackEntry>> = _backStack.asStateFlow()

    fun initStartDestinations(graph: List<Destination>) {
        _backStack.update {
            // TODO : startDestination 으로 변경해야 한다
            it + NavBackStackEntry(graph.first())
        }
    }

    class Destination(
        val content: @Composable () -> Unit
    )
}
