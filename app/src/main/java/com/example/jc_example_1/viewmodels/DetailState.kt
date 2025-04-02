package com.example.jc_example_1.viewmodels

import com.example.jc_example_1.models.ContactModel
import com.example.jc_example_1.models.User

sealed class DetailUIState{
    object Init : DetailUIState()
    object Loading : DetailUIState()
    data class Success(val contactModel: ContactModel?) : DetailUIState()
    data class Error(val errorMessage: String?) : DetailUIState()
}