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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepo: AuthRepository,
    application: Application,
    private val dataStoreManager: DTaStoreManager
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow<LoginState>(LoginState())

    val state: StateFlow<LoginState> = _state

    init {
        _state.value =  _state.value.copy(username = "037193002652", password = "Tam@12345")
    }

    fun resetState() {
        _state.value = LoginState()
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnterUsername -> _state.value = _state.value.copy(username = event.username)
            is LoginEvent.EnterPassword ->_state.value = _state.value.copy(password = event.password)
            is LoginEvent.Submit -> onLogin()
        }
    }

    fun onLogin() {
        var errorMessage: String? = null

        if (_state.value.username.isBlank() || _state.value.password.isBlank()) {
            _state.value =   _state.value.copy(error = "Username or Password is not empty.")
            return
        }else{
            _state.value = _state.value.copy(error = null)
        }
        viewModelScope.launch {
            //show loading
            _state.value =   _state.value.copy(isLoading = true)

            try {
                val result =
                    authRepo.login(
                        LoginRequest(
                            identityNo = _state.value.username,
                            password = _state.value.password
                        )
                    )
                if (result is ApiResult.Success) {

                    //save accessToken to data_store
                    dataStoreManager.putString(Const.ACCESS_TOKEN, result.data?.data ?: "")
                    //save identityNo
                    dataStoreManager.putString(Const.IDENTITY_NO, _state.value.username)

                    _state.value = _state.value.copy(loginResponse = result.data, isLoading = false, error = null)
                } else if (result is ApiResult.Error) {
                    errorMessage =
                        if (result.message.isNullOrEmpty()) "Network not connected" else result.message
                    _state.value = _state.value.copy(error = errorMessage, isLoading = false)
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: ""
                _state.value =  _state.value.copy(error = errorMessage, isLoading = false)
            }
        }
    }
}