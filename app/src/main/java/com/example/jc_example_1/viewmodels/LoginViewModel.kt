package com.example.jc_example_1.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.jc_example_1.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
  var user by mutableStateOf<User?>(null)
      private set
    fun login(username: Int, password: String) {

        user = User(id = username, name = password)
    }
}