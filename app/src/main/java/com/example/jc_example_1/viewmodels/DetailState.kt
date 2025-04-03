package com.example.jc_example_1.viewmodels

import com.example.jc_example_1.models.ContactModel
import com.example.jc_example_1.models.LoginResponse
import com.example.jc_example_1.models.User

sealed class DetailState{
    data class Init(val user : User? = null) : DetailState()
    object Loading : DetailState()
    data class Success(val contactModel: ContactModel?) : DetailState()
    data class Error(val errorMessage: String?) : DetailState()
}

sealed class DetailEvent{
    data class onInit(val user: User?) : DetailEvent()
    object onUpdateContact : DetailEvent()
}
