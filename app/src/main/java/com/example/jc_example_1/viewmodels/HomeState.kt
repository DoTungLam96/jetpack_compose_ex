package com.example.jc_example_1.viewmodels

import com.example.jc_example_1.models.LoginResponse
import com.example.jc_example_1.models.User

sealed class HomeState{
    object Init : HomeState()
    object Loading : HomeState()
    data class NavHome(val user: User?)  : HomeState()
    data class Success(val user: User?) : HomeState()
    data class Error(val errorMessage: String?) : HomeState()
}

sealed class HomeEvent{
    object onGetUserInfo : HomeEvent()
    object onContinue : HomeEvent()
}


