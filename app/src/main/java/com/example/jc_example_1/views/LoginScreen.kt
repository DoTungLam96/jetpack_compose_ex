package com.example.jc_example_1.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.jc_example_1.models.Routes
import com.example.jc_example_1.models.User
import com.example.jc_example_1.viewmodels.ShareViewModel

@Composable
fun LoginScreen(navController: NavHostController, viewModel: ShareViewModel = hiltViewModel()) {

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Button(
            onClick = {
                val user = User(id = 1, name = "Lam Do");

//                navController.currentBackStackEntry?.savedStateHandle?.set("user", user)

                viewModel.updateUser(user)

                navController.navigate(Routes.HOME_SCREEN)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF009688)
            ), // Màu cam
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.height(52.dp),
        ) {
            Text(text = "Go to HomeScreen")
        }
    }

}