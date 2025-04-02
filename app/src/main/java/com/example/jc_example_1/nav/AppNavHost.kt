package com.example.jc_example_1.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import androidx.navigation.navArgument
import com.example.jc_example_1.models.Const
import com.example.jc_example_1.models.User
import com.example.jc_example_1.views.DetailScreen
import com.example.jc_example_1.views.HomeScreen
import com.example.jc_example_1.views.LoginScreen
import com.example.jc_example_1.views.OtherScreen
import com.google.gson.Gson

@Composable
fun AppNavHost(navController: NavHostController) {
    val navGraph = navController.createGraph(startDestination = Const.LOGIN_SCREEN) {
        composable(
            Const.LOGIN_SCREEN,

            ) {
            LoginScreen(navController = navController)
        }
        composable(
            route = Const.HOME_SCREEN

        ) {
            HomeScreen(navController = navController)
        }
        composable(
            route = "${Const.DETAIL_SCREEN}/{${Const.USER_ARGUMENT_KEY}}",
            arguments = listOf(navArgument(Const.USER_ARGUMENT_KEY) { type = NavType.StringType })
        ) {
                backStackEntry ->
            val userJson = backStackEntry.arguments?.getString(Const.USER_ARGUMENT_KEY)

            val user = try {
                Gson().fromJson(userJson, User::class.java)
            } catch (e: Exception) {
                null
            }
            DetailScreen(navController = navController, user = user)
        }
        composable(Const.OTHER_SCREEN) {
            OtherScreen(navController = navController)
        }


    }
    NavHost(navController = navController, graph = navGraph)
}