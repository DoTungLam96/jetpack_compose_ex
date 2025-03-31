package com.example.jc_example_1.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jc_example_1.models.Const
import com.example.jc_example_1.models.LoginRequest
import com.example.jc_example_1.models.User
import com.example.jc_example_1.repository.AuthRepository
import com.example.jc_example_1.storage.DTaStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepo: AuthRepository,
    application: Application,
    private val dataStoreManager: DTaStoreManager
) : AndroidViewModel(application) {
    var loginState by mutableStateOf<LoginUiState>(LoginUiState.Init)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var username by mutableStateOf<String>("037193002652")
        private set
    var password by mutableStateOf<String>("Tam@12345")
        private set
    fun onSetUsername(text: String) {
        username = text;
    }

    fun onSetPassword(text: String) {
        password = text;
    }

    fun resetState() {
        loginState = LoginUiState.Init
        errorMessage = null

    }

    fun onLoginClicked(username: String?, password: String?) {
        errorMessage = null

        if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
            errorMessage = "Username or Password is not empty."

            return
        }
        viewModelScope.launch {
            loginState = LoginUiState.Loading

            try {
                val result =
                    authRepo.login(LoginRequest(identityNo = username, password = password))
                if (result is ApiResult.Success) {

                    //save accessToken to data_store
                    dataStoreManager.putString(Const.ACCESS_TOKEN, result.data?.data ?: "")

                    loginState = LoginUiState.Success(result.data)
                } else if (result is ApiResult.Error) {
                    errorMessage =
                        if (result.message.isNullOrEmpty()) "Network not connected" else result.message
                    loginState = LoginUiState.Error(errorMessage!!)
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: ""
                loginState = LoginUiState.Error(errorMessage!!)
            }
        }
    }

}