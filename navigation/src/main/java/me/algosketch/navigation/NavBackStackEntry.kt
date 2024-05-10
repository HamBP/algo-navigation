package me.algosketch.navigation

import android.os.Bundle

class NavBackStackEntry(
    var destination: NavDestination,
    private val immutableArgues: Bundle? = null,
) {
    val arguments: Bundle?
        get() = if (immutableArgues == null) {
            null
        } else {
            Bundle(immutableArgues)
        }
}
