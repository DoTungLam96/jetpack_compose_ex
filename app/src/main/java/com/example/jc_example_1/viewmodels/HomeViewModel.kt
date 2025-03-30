package com.example.jc_example_1.viewmodels

import DataStoreManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jc_example_1.models.LoginRequest

import com.example.jc_example_1.models.User
import com.example.jc_example_1.repository.AuthRepository
import com.example.jc_example_1.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepo: UserRepository,
//    private val dataStoreManager: DataStoreManager
) : ViewModel() {

//    @Inject
//    lateinit var dataStoreManager: DataStoreManager
    var user by mutableStateOf<User?>(null)
        private set

    init {
        getUserInfo()
    }

    fun getUserInfo() {

        viewModelScope.launch {
            try {
                val result = userRepo.getUserInfo()
                if (result is ApiResult.Success) {
                    print(result.data)
                } else if (result is ApiResult.Error) {
                    print(result)
                }
            } catch (_: Exception){}
        }
    }

}