package com.example.jc_example_1.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jc_example_1.models.LoginRequest

import com.example.jc_example_1.models.User
import com.example.jc_example_1.repository.AuthRepository
import com.example.jc_example_1.repository.UserRepository
import com.example.jc_example_1.storage.DTaStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepo: UserRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<HomeState>(HomeState.Init)
    val state: StateFlow<HomeState> = _state

    private var user: User? = null

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.onGetUserInfo -> getUserInfo()
            is HomeEvent.onContinue -> onContinue()
        }
    }

    fun resetState(){
        _state.value = HomeState.Init
    }


    private fun onContinue() {
        _state.value = HomeState.NavHome(user)
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            try {

                _state.value = HomeState.Loading
                val result = userRepo.getUserInfo()

                if (result is ApiResult.Success) {
                    user = result.data
                    _state.value = HomeState.Success(result.data)
                } else if (result is ApiResult.Error) {
                    user = null
                    _state.value = HomeState.Error(result.message)
                }
            } catch (_: Exception) {
                user = null
            }
        }
    }
}