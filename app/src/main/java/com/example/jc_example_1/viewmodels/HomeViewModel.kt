package com.example.jc_example_1.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.jc_example_1.models.AccountModel
import com.example.jc_example_1.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    var account by mutableStateOf<AccountModel?>(null)
        private set
    fun updateAccountModel(acc: AccountModel?){
        acc.let {
            account = it
        }
    }
}