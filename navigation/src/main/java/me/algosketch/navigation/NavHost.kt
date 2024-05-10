package me.algosketch.navigation

import androidx.activity.compose.BackHandler
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
    NavHost(
        navController,
        remember(startDestination, builder) {
            navController.createGraph(
                NavGraphBuilder(
                    startDestination
                ).apply(builder)
            )
        },
    )
}

@Composable
fun NavHost(
    navController: NavHostController,
    graph: NavGraph,
) {
    navController.graph = graph

    val currentBackStack by navController.navigatorProvider[ComposeNavigator.NAME]!!.backStack.collectAsState()
    val backStackEntry = currentBackStack.last()
    (backStackEntry.destination as ComposeNavigator.Destination).content(backStackEntry)

    BackHandler(currentBackStack.size > 1) {
        navController.popBackStack()
    }
}
