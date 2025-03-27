package com.example.jc_example_1.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jc_example_1.models.User
import com.example.jc_example_1.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepo: AuthRepository
) : ViewModel() {
    var loginState by mutableStateOf<LoginUiState>(LoginUiState.Init)
        private set
    var user by mutableStateOf<User?>(null)
        private set

    fun login(username: Int, password: String) {
    }

    fun resetState() {
        loginState = LoginUiState.Init
    }
    fun onLoginClicked() {
        viewModelScope.launch {
            loginState = LoginUiState.Loading

            try {
                val result = authRepo.getComments(postId = 1)
                if (result is ApiResult.Success) {
                    loginState = LoginUiState.getComments(result.data)
                } else if (result is ApiResult.Error) {
                    loginState = LoginUiState.Error( if (result.message.isNullOrEmpty()) "Network not connected" else result.message)
                }
            } catch (e: Exception) {
                loginState = LoginUiState.Error(e.message ?: "")
            }
        }
    }
}