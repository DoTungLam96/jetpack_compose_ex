package com.example.jc_example_1.views

import CustomCenterTopAppBar
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.jc_example_1.models.Routes
import com.example.jc_example_1.models.User
import com.example.jc_example_1.viewmodels.ShareViewModel

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun DetailScreen(navController: NavHostController, user: User? = null) {
    val context = LocalContext.current
    val parentEntry = navController.getBackStackEntry(Routes.LOGIN_SCREEN)


    val sharedViewModel: ShareViewModel = hiltViewModel(parentEntry)
    Scaffold(topBar = {
        CustomCenterTopAppBar(title = "Detail", onBackClick = {
            navController.popBackStack()
        })
    }) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Welcome ${sharedViewModel.user?.name}")
                Button(
                    modifier = Modifier.padding(top = 16.dp),
                    onClick = {

                    navController.navigate(Routes.OTHER_SCREEN)

                }) {
                    Text(text = "Go to Others")
                }
            }

        }
    }
}