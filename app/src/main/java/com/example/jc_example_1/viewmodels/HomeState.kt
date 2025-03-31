package com.example.jc_example_1.viewmodels

import com.example.jc_example_1.models.LoginResponse
import com.example.jc_example_1.models.User

sealed class HomeUIState{
    object Init : HomeUIState()
    object Loading : HomeUIState()
    data class Success(val user: User?) : HomeUIState()
    data class Error(val errorMessage: String?) : HomeUIState()
}


