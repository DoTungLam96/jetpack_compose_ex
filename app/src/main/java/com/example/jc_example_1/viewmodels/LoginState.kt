package com.example.jc_example_1.viewmodels

import com.example.jc_example_1.models.User

// Các trạng thái của Login
sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val user: User) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}