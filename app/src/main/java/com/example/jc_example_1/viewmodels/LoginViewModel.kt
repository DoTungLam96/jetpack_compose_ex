package com.example.jc_example_1.viewmodels

import Lam
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.jc_example_1.models.User
import com.example.jc_example_1.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepo: AuthRepository
) : ViewModel() {

  var user by mutableStateOf<User?>(null)
      private set
    fun login(username: Int, password: String) {

//        user = User(id = username, name = password)
    }

    fun getComments(postId: Int){


      val nam: Lam.Nam = Lam.Nam(id = 1);

        print(nam)
//        authRepo.getComments(postId);
    }
}