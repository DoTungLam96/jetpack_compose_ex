package com.example.jc_example_1.viewmodels

import com.example.jc_example_1.models.Comment
import com.example.jc_example_1.models.LoginResponse
import com.example.jc_example_1.models.User

//// Các trạng thái của Login
//sealed class LoginUiState {
//    object Init : LoginUiState()
//    object Loading : LoginUiState()
//    data class Success(val data: LoginResponse?) : LoginUiState()
//    data class Error(val message: String) : LoginUiState()
//}
sealed class LoginEvent {
    data class EnterUsername(val username: String) : LoginEvent()
    data class EnterPassword(val password: String) : LoginEvent()
    object Submit : LoginEvent()
}

data class LoginState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val loginResponse: LoginResponse?  = null,
)

