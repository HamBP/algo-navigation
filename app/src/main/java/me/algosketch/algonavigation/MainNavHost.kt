package me.algosketch.algonavigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.algosketch.algonavigation.ui.theme.AlgoNavigationTheme

@Composable
fun MainNavHost() {
    val navController = rememberNavController()

    NavHost(navController) {
        composable {
            Greeting(name = "Android")
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
