package me.algosketch.navigation

import android.os.Bundle
import androidx.annotation.CallSuper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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
        args: Bundle?,
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
