package me.algosketch.algonavigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun NavHost(
    navController: NavHostController,
    content: @Composable () -> Unit,
) {
    val composeNavigator = navController.navigator
    composeNavigator.initStartDestination(content)

    val currentBackStack by composeNavigator.backStack.collectAsState()

    currentBackStack.last().destination.content()
}
