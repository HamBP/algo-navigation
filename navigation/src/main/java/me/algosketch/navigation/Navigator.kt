package me.algosketch.navigation

import android.os.Bundle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class Navigator<D : NavDestination>(val name: String) {
    private val _backStack: MutableStateFlow<List<NavBackStackEntry>> = MutableStateFlow(listOf())
    val backStack: StateFlow<List<NavBackStackEntry>> = _backStack.asStateFlow()

    open fun navigate(
        entry: NavBackStackEntry,
        args: Bundle?,
    ) {
        _backStack.update {
            it + entry
        }
    }

    fun popBackStack(popUpTo: NavBackStackEntry) {
        _backStack.update {
            it.takeWhile { backStack -> backStack != popUpTo }
        }
    }
}
