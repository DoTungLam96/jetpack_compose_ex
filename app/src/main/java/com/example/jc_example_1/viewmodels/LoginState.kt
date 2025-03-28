package com.example.jc_example_1.viewmodels

import com.example.jc_example_1.models.Comment
import com.example.jc_example_1.models.LoginResponse
import com.example.jc_example_1.models.User

// Các trạng thái của Login
sealed class LoginUiState {
    object Init : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val data: LoginResponse?) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}