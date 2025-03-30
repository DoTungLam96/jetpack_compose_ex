package com.example.jc_example_1.views

import CustomCenterTopAppBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.jc_example_1.models.Const
import com.example.jc_example_1.models.User
import com.example.jc_example_1.viewmodels.ShareViewModel

@Composable
fun OtherScreen(navController: NavHostController, user: User? = null) {
//    val context = LocalContext.current
    val viewModel: ShareViewModel = hiltViewModel()
    Scaffold(topBar = {
        CustomCenterTopAppBar(title = "Other Screen", onBackClick = {
//            navController.popBackStack(route = Const.HOME_SCREEN, inclusive = true)

            navController.navigate(Const.HOME_SCREEN) {
                popUpTo("login") { inclusive = false }
                launchSingleTop = true
            }
        })
    }) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), color = MaterialTheme.colorScheme.background
        ) {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text(text = "Other Screen!")
                Text(text = "Hello ${viewModel.user?.userId}", modifier = Modifier.padding(top = 10.dp))
            }

        }
    }
}