package me.algosketch.algonavigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberNavController(): NavHostController {
    return remember {
        createNavController()
    }
}

private fun createNavController() = NavHostController()

open class NavHostController {
    val navigator = ComposeNavigator()
}
