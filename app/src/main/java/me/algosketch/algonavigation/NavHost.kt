package me.algosketch.algonavigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

@Composable
fun NavHost(
    navController: NavHostController,
    startDestination: String,
    builder: NavGraphBuilder.() -> Unit,
) {
    val graph = remember {
        navController.createGraph(NavGraphBuilder(startDestination).apply(builder))
    }
    navController.graph = graph

    val currentBackStack by navController.navigatorProvider[ComposeNavigator.NAME]!!.backStack.collectAsState()
    (currentBackStack.last().destination as ComposeNavigator.Destination).content()
}
