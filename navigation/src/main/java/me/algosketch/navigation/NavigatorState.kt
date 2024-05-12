package me.algosketch.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class NavigatorState {
    private val _backStack: MutableStateFlow<List<NavBackStackEntry>> = MutableStateFlow(listOf())
    val backStack: StateFlow<List<NavBackStackEntry>> = _backStack.asStateFlow()

    open fun push(backStackEntry: NavBackStackEntry) {
        _backStack.value += backStackEntry
    }

    fun pop(popUpTo: NavBackStackEntry) {
        _backStack.value = _backStack.value.takeWhile { it != popUpTo }
    }
}
