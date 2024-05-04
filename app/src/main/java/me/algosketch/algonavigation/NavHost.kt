package me.algosketch.algonavigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

@Composable
fun NavHost(
    navController: NavHostController,
    builder: NavGraphBuilder.() -> Unit,
) {
    val graph = remember {
        navController.createGraph(NavGraphBuilder().apply(builder))
    }

    val composeNavigator = navController.navigator
    composeNavigator.initStartDestinations(graph)

    // 출력
    val currentBackStack by composeNavigator.backStack.collectAsState()
    currentBackStack.last().destination.content()
}
