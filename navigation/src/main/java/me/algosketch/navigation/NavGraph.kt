package me.algosketch.navigation

class NavGraph : NavDestination("navigation") {
    val nodes = mutableListOf<NavDestination>()
    var startDestinationRoute: String? = null
}
