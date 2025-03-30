package com.example.jc_example_1.views

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.jc_example_1.models.Const
import com.example.jc_example_1.models.User
import com.example.jc_example_1.viewmodels.LoginUiState
import com.example.jc_example_1.viewmodels.LoginViewModel
import com.example.jc_example_1.viewmodels.ShareViewModel

@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {

    val loginState = viewModel.loginState

    val context = LocalContext.current

    // Cập nhật messageError dựa vào loginState
    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginUiState.Init, is LoginUiState.Loading -> Unit
            is LoginUiState.Error -> {
                Toast.makeText(context, loginState.message, Toast.LENGTH_LONG).show()
            }

            is LoginUiState.Success -> {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    "Const.ACCESS_TOKEN", loginState.data
                )
                navController.navigate(Const.HOME_SCREEN) {
                    // Nếu không muốn quay lại LoginScreen, loại bỏ nó khỏi back stack
                    popUpTo(Const.LOGIN_SCREEN) { inclusive = true }
                }

            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        _buildBody(loginState)
    }
}

@Composable
private fun _buildBody(loginState: LoginUiState) {
    val viewModel: LoginViewModel = hiltViewModel()
    OutlinedTextField(value = viewModel.username, onValueChange = {
        viewModel.onSetUsername(it)
    }, label = { Text("Username") }, modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(12.dp))
    OutlinedTextField(
        value = viewModel.password,
        onValueChange = {
        viewModel.onSetPassword(it)
    },
        label = { Text("Password") },
      
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(20.dp))
    if (loginState is LoginUiState.Loading) {
        Spacer(modifier = Modifier.height(16.dp))
        CircularProgressIndicator()
    } else Button(
        onClick = {
            viewModel.onLoginClicked(username = viewModel.username, password = viewModel.password)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF009688)
        ), // Màu cam
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.height(52.dp),
    ) {
        Text(text = "Login", color = Color.White)
    }
    Spacer(modifier = Modifier.height(8.dp))

    viewModel.errorMessage?.let {
        Text(
            text = it, color = Color.Red, modifier = Modifier.padding(top = 8.dp)
        )
    }
}