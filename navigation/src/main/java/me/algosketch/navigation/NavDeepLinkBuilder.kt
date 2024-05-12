package me.algosketch.navigation

fun navDeepLink(deepLinkBuilder: NavDeepLink.Builder.() -> Unit): NavDeepLink =
    NavDeepLink.Builder().apply(deepLinkBuilder).build()
