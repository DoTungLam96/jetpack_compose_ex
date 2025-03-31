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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val dataStoreManager: DTaStoreManager
) : ViewModel() {

//    var user by mutableStateOf<User?>(null)
//        private set

    var homeUIState by mutableStateOf<HomeUIState>(HomeUIState.Init)
        private set

    init {
        getUserInfo()
    }

    fun getUserInfo() {

        viewModelScope.launch {
            try {

                homeUIState = HomeUIState.Loading

                val result = userRepo.getUserInfo()

                if (result is ApiResult.Success) {
                    homeUIState = HomeUIState.Success(result.data)
                } else if (result is ApiResult.Error) {
                    homeUIState = HomeUIState.Error(result.message)
                }
            } catch (_: Exception){

            }
        }
    }

}