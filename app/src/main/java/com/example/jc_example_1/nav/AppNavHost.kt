package com.example.jc_example_1.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import com.example.jc_example_1.models.Routes
import com.example.jc_example_1.models.User
import com.example.jc_example_1.views.DetailScreen
import com.example.jc_example_1.views.HomeScreen
import com.example.jc_example_1.views.LoginScreen
import com.example.jc_example_1.views.OtherScreen

@Composable
fun AppNavHost(navController: NavHostController){
    val navGraph = navController.createGraph(startDestination = Routes.LOGIN_SCREEN) {
        composable(Routes.LOGIN_SCREEN) {
            LoginScreen(navController = navController)
        }
        composable(Routes.HOME_SCREEN) {
          //  val user = navController.previousBackStackEntry?.savedStateHandle?.get<User>("user")

            HomeScreen(navController = navController)
        }
        composable(Routes.DETAIL_SCREEN) {
          //  val user = navController.previousBackStackEntry?.savedStateHandle?.get<User>("user")
            DetailScreen(navController = navController)
        }
        composable(Routes.OTHER_SCREEN) {
            OtherScreen(navController = navController)
        }


    }
    NavHost(navController = navController, graph = navGraph )
}