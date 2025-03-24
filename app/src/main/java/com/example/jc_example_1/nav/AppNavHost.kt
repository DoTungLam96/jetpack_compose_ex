package com.example.jc_example_1.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import com.example.jc_example_1.User
import com.example.jc_example_1.views.DetailScreen
import com.example.jc_example_1.views.HomeScreen

@Composable
fun AppNavHost(navController: NavHostController){
    val navGraph = navController.createGraph(startDestination = "homeScreen") {
        composable("homeScreen") {
            HomeScreen(navController = navController)
        }
        composable("detailScreen") {
            val user = navController.previousBackStackEntry?.savedStateHandle?.get<User>("user")
            DetailScreen(navController = navController, user = user)
        }
    }
    NavHost(navController = navController, graph = navGraph )
}