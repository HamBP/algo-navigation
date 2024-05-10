package me.algosketch.navigation

open class NavDestination(
    val navigatorName: String
) {
    private val deepLinks = mutableListOf<NavDeepLink>()
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

    fun addDeepLink(uriPattern: String) {
        deepLinks.add(NavDeepLink(uriPattern))
    }

    fun createRoute(route: String?): String =
        if (route != null) "android-app://androidx.navigation/$route" else ""
}
