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
import com.example.jc_example_1.models.Routes
import com.example.jc_example_1.models.User
import com.example.jc_example_1.viewmodels.LoginUiState
import com.example.jc_example_1.viewmodels.LoginViewModel
import com.example.jc_example_1.viewmodels.ShareViewModel

@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {

    // Quan sát trạng thái user từ ViewModel
    val user = viewModel.user

    val loginState = viewModel.loginState

    var isLoading by remember { mutableStateOf<Boolean>(true) }

    val context = LocalContext.current

    var messageError by remember { mutableStateOf<String?>(null) }

    // Cập nhật messageError dựa vào loginState
    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginUiState.Loading -> isLoading = true
            is LoginUiState.Error -> {
                messageError = loginState.message
                isLoading = false
//                viewModel.resetState()
            }
            is LoginUiState.getComments -> {
                isLoading = false
                navController.currentBackStackEntry
                    ?.savedStateHandle?.set("lstComment", loginState.lstComment)
                navController.navigate(Routes.HOME_SCREEN) {
                    // Nếu không muốn quay lại LoginScreen, loại bỏ nó khỏi back stack
                    popUpTo(Routes.LOGIN_SCREEN) { inclusive = true }
                }
                messageError = null
            }
            else -> {
                isLoading = false
                messageError = null
                viewModel.resetState()
            }
        }
    }


    // Khi user không null (đăng nhập thành công) thì chuyển sang HomeScreen
    if (user != null) {
        LaunchedEffect(user) {
            // Lưu dữ liệu user qua SavedStateHandle của backStackEntry
            navController.currentBackStackEntry?.savedStateHandle?.set("user", user)
            // Chuyển sang màn Home và loại bỏ màn Login khỏi back stack nếu cần
            navController.navigate(Routes.HOME_SCREEN){
                popUpTo(Routes.LOGIN_SCREEN) { inclusive = false }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
      if (isLoading) {
          Spacer(modifier = Modifier.height(16.dp))
          CircularProgressIndicator()
      } else  Button(
            onClick = {
                viewModel.onLoginClicked()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF009688)
            ), // Màu cam
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.height(52.dp),
        ) {
            Text(text = "Go to HomeScreen")
        }
        Spacer(modifier = Modifier.height(8.dp))

        messageError?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }


    }

}