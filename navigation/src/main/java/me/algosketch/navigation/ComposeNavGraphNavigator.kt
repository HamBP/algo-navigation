package me.algosketch.navigation

class ComposeNavGraphNavigator(
    private val navigatorProvider: Map<String, Navigator<out NavDestination>>
) : Navigator<NavGraph>("navigation") {

    override fun navigate(
        entry: NavBackStackEntry,
    ) {
        super.navigate(entry)
        val args = entry.arguments
        val destination = entry.destination as NavGraph
        val startRoute = destination.startDestinationRoute!!
        val startDestination = destination.nodes.find { it.route == startRoute }!!
        val backStackEntry = NavBackStackEntry(startDestination, args)

        val navigator = navigatorProvider[startDestination.navigatorName]!!
        navigator.navigate(backStackEntry)
    }
}
