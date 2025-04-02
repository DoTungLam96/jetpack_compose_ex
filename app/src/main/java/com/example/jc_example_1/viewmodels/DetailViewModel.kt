package com.example.jc_example_1.viewmodels

import ApiResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jc_example_1.models.Const
import com.example.jc_example_1.models.ContactModel
import com.example.jc_example_1.models.User
import com.example.jc_example_1.repository.UserRepository
import com.example.jc_example_1.storage.DTaStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val userRepository: UserRepository,
    private val dataStoreManager: DTaStoreManager
) : ViewModel() {

//    var detailState by mutableStateOf<DetailUIState>(DetailUIState.Init)
//        private set
//    var contactModel by mutableStateOf<ContactModel?>(null)
//        private set

//    private var user : User? = null
//
//    fun setUserData(currentUser : User?){
//        user = currentUser
//    }

    private  val _state = MutableStateFlow<DetailState>(DetailState())
    val state : StateFlow<DetailState> = _state

    init {
//        getContact()

        _state.value = _state.value.copy(isInitial = true)
    }



    fun onEvent(event: DetailEvent){
        when(event)
        {
            is DetailEvent.onGetContact -> getContact()
            is DetailEvent.onInit -> {
                print(event)
                _state.value = _state.value.copy(user = event.user)
                getContact()
            }
        }
    }

    fun resetState() {
//        detailState = DetailUIState.Init
    }

    fun getContact() {
        viewModelScope.launch {

//            detailState = DetailUIState.Loading

            _state.value = _state.value.copy(isLoading = true, isInitial = false, errorMessage = null)

            val identityNo = dataStoreManager.getString(Const.IDENTITY_NO).first()

            if (identityNo.isEmpty()) return@launch

            val result = userRepository.getContact(identityNo)

            if (result is ApiResult.Success) {

//                contactModel = result.data
                _state.value = _state.value.copy(isLoading = false, contactModel = result.data)

//                detailState = DetailUIState.Success(result.data)
            }
            if (result is ApiResult.Error){
                _state.value = _state.value.copy(isLoading = false, errorMessage = result.message)
//                detailState = DetailUIState.Error(result.message)
            }

        }
    }

//
}