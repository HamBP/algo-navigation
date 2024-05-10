package me.algosketch.navigation

import android.os.Bundle

class DeepLinkMatch(
    val destination: NavDestination,
    val matchingArgs: Bundle?,
) : Comparable<DeepLinkMatch> {

    // TODO : 우선순위에 맞게 변경해야 함
    override fun compareTo(other: DeepLinkMatch): Int {
        return 1
    }
}
