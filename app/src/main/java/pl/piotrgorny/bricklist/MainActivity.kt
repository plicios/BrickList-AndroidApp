package pl.piotrgorny.bricklist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pl.piotrgorny.bricklist.ui.screen.MissingPartsScreen
import pl.piotrgorny.bricklist.ui.screen.SetScreen
import pl.piotrgorny.bricklist.ui.screen.SetsScreen
import pl.piotrgorny.bricklist.ui.theme.BrickListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BrickListTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = "sets") {
                        composable("sets") {
                            SetsScreen(
                                navigateToSet = { setId ->
                                    navController.navigate("set/$setId")
                                },
                                navigateToMissingParts = {
                                    navController.navigate("missingParts")
                                }
                            )
                        }
                        composable("set/{setId}", listOf(
                            navArgument("setId") {
                                type = NavType.StringType
                            }
                        )) {
                            val setId = it.arguments?.getString("setId")
                            setId?.let {
                                SetScreen(setId)
                            }
                        }
                        composable("missingParts") {
                            MissingPartsScreen()
                        }
                    }

                }
            }
        }
    }
}





