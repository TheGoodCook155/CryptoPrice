package com.crypto.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.crypto.screens.MainScreen
import com.crypto.screens.PickScreen
import com.crypto.screens.SplashScreen

@Composable
fun CryptoNavigation(){

    val navController = rememberNavController()
//TODO change back to SPLASH
    NavHost(navController = navController, startDestination = Screens.SPLASH_SCREEN.name) {

        composable(Screens.SPLASH_SCREEN.name) {
            SplashScreen(navController = navController)
        }

        composable(Screens.PICK_SCREEN.name) {
                PickScreen(navController = navController)
        }


              val routeMainScreen = Screens.MAIN_SCREEN.name
            composable("$routeMainScreen/{value}", listOf(
                navArgument("value"){
                    type = NavType.StringType
                },
            )
            ) { navBack ->

                    val value = navBack.arguments?.getString("value")

                    Log.d("valuesReceived", "CryptoNavigation: ${value.toString()}")
                MainScreen(navController = navController, param = value.toString())

            }
    }
}