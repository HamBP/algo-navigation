package me.algosketch.navigation

import androidx.annotation.CallSuper

abstract class Navigator<D : NavDestination>(val name: String) {
    private var _state: NavigatorState? = null
    protected val state: NavigatorState
        get() = checkNotNull(_state) {
            "You cannot access the Navigator's state until the Navigator is attached"
        }

    var isAttached: Boolean = false
        private set

    open fun navigate(
        entry: NavBackStackEntry,
    ) {
        state.push(entry)
    }

    fun popBackStack(popUpTo: NavBackStackEntry) {
        state.pop(popUpTo)
    }

    @CallSuper
    open fun onAttach(state: NavigatorState) {
        _state = state
        isAttached = true
    }
}
