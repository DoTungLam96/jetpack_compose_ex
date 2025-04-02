package com.example.jc_example_1.viewmodels

import com.example.jc_example_1.models.ContactModel
import com.example.jc_example_1.models.LoginResponse
import com.example.jc_example_1.models.User

sealed class DetailUIState{
    object Init : DetailUIState()
    object Loading : DetailUIState()
    data class Success(val contactModel: ContactModel?) : DetailUIState()
    data class Error(val errorMessage: String?) : DetailUIState()
}

sealed class DetailEvent{
    object onGetContact : DetailEvent()
    data class onInit(val user: User?) : DetailEvent()
}

data class DetailState(val isLoading: Boolean = false,
                       val isInitial: Boolean = false,
                       val user : User? = null,
                       val errorMessage: String? = null,
                       val contactModel: ContactModel?  = null, )