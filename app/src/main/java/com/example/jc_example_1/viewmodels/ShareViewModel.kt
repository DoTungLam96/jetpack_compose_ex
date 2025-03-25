package com.example.jc_example_1.viewmodels

import android.accounts.Account
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.jc_example_1.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShareViewModel @Inject constructor() : ViewModel() {
    var user by mutableStateOf<User?>(null)
    private set

    fun setUser(newUser: User?) {
        newUser?.let {
            user = it
        }
    }
}