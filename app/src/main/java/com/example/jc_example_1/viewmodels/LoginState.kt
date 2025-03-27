package com.example.jc_example_1.viewmodels

import com.example.jc_example_1.models.Comment
import com.example.jc_example_1.models.User

// Các trạng thái của Login
sealed class LoginUiState {
    object Init : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val user: User) : LoginUiState()

    data class getComments(val lstComment: List<Comment>?) :  LoginUiState()
    data class Error(val message: String) : LoginUiState()
}