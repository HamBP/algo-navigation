package me.algosketch.navigation

open class NavDestination(
    val navigatorName: String
) {
    private val deepLinks = mutableListOf<NavDeepLink>()
    private var _arguments: MutableMap<String, NamedNavArgument> = mutableMapOf()
    val arguments = _arguments.toMap()
    var parent: NavGraph? = null

    /**
     * NavGraph는 route를 갖지 않지만, startRoute로 startDestination을 찾으려면
     * 상위 클래스인 NavDestination에서 route를 갖고 있어야 findNode 메서드를 구현할 수 있다.
     */
    var route: String? = null
        set(value) {
            if (value != null) {
                val internalRoute = createRoute(value)
                addDeepLink(internalRoute)
            }

            field = value
        }

    fun addArgument(argumentName: String, argument: NamedNavArgument) {
        _arguments[argumentName] = argument
    }

    fun addDeepLink(uriPattern: String) {
        addDeepLink(NavDeepLink(uriPattern))
    }

    fun addDeepLink(navDeepLink: NavDeepLink) {
        deepLinks.add(navDeepLink)
    }

    open fun matchDeepLink(navDeepLinkRequest: NavDeepLinkRequest): DeepLinkMatch? {
        if (deepLinks.isEmpty()) {
            return null
        }

        for (deepLink in deepLinks) {
            val uri = navDeepLinkRequest.uri.toString()
            val matchingArguments = deepLink.getMatchingArguments(uri, _arguments)

            if (matchingArguments != null || deepLink.uriPattern == uri) {
                val newMatch = DeepLinkMatch(this, matchingArguments)
                // TODO : early return 대신 deeplinks 중에서 bestMatch를 찾아야 함
                return newMatch
            }
        }

        return null
    }

    fun hasRoute(route: String): Boolean {
        return this.route == route
    }

    companion object {
        fun createRoute(route: String?): String =
            if (route != null) "android-app://androidx.navigation/$route" else ""
    }
}
