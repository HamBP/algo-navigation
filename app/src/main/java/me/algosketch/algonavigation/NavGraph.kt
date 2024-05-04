package me.algosketch.algonavigation

class NavGraph() : NavDestination("navigation") {
    val nodes = mutableListOf<NavDestination>()
    var startDestinationRoute: String? = null
}
