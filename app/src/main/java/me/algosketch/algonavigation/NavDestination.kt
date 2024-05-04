package me.algosketch.algonavigation

open class NavDestination(
    val navigatorName: String
) {
    /**
     * NavGraph는 route를 갖지 않지만, startRoute로 startDestination을 찾으려면
     * 상위 클래스인 NavDestination에서 route를 갖고 있어야 findNode 메서드를 구현할 수 있다.
     */
    var route: String? = null
}
