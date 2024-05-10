package me.algosketch.navigation

class NavGraph : NavDestination("navigation") {
    val nodes = mutableListOf<NavDestination>()
    var startDestinationRoute: String? = null

    override fun matchDeepLink(uri: String): DeepLinkMatch? {
        val bestMatch = super.matchDeepLink(uri)
        val bestChildMatch = nodes.mapNotNull { child ->
            child.matchDeepLink(uri)
        }.maxOrNull()

        return listOfNotNull(bestMatch, bestChildMatch).maxOrNull()
    }
}
