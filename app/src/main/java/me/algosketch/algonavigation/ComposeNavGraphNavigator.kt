package me.algosketch.algonavigation

class ComposeNavGraphNavigator(
    private val navigatorProvider: Map<String, Navigator<out NavDestination>>
) : Navigator<NavGraph>("navigation") {


    override fun navigate(entry: NavBackStackEntry) {
        super.navigate(entry)
        val destination = entry.destination as NavGraph
        val startRoute = destination.startDestinationRoute!!
        val startDestination = destination.nodes.find { it.route == startRoute }!!
        val backStackEntry = NavBackStackEntry(startDestination)

        val navigator = navigatorProvider[startDestination.navigatorName]!!
        navigator.navigate(backStackEntry)
    }
}
