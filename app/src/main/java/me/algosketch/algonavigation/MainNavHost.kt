package me.algosketch.algonavigation

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.algosketch.algonavigation.ui.theme.AlgoNavigationTheme
import me.algosketch.navigation.NavHost
import me.algosketch.navigation.NavType
import me.algosketch.navigation.composable
import me.algosketch.navigation.navArgument
import me.algosketch.navigation.rememberNavController

@Composable
fun MainNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            Greeting(
                modifier = Modifier.clickable {
                    navController.navigate("detail/3")
                },
                name = "Android"
            )
        }

        composable(
            route = "detail/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { entry ->
            val id = entry.arguments!!.getInt("id")
            Greeting(name = "Android Android $id")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AlgoNavigationTheme {
        Greeting("Android")
    }
}
