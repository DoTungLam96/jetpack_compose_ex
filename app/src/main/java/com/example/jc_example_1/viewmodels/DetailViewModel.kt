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

    private val _state = MutableStateFlow<DetailState>(DetailState.Init())
    val state: StateFlow<DetailState> = _state
    private var _user: User? = null
    private var _currentContact: ContactModel? = null

    val currentContact: ContactModel?
        get() = _currentContact

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.onInit -> {
                _user = event.user

                getContact()
            }

            is DetailEvent.onUpdateContact -> {
                updateContact()
            }
        }
    }

    fun getContact() {
        viewModelScope.launch {

            _state.value = DetailState.Loading

            val identityNo = dataStoreManager.getString(Const.IDENTITY_NO).first()

            if (identityNo.isEmpty()) return@launch

            val result = userRepository.getContact(identityNo)

            if (result is ApiResult.Success) {
                _currentContact = result.data
                _state.value = DetailState.Success(contactModel = result.data)
            }
            if (result is ApiResult.Error) {
                _state.value = DetailState.Error(errorMessage = result.message)
            }

        }
    }

    private fun updateContact() {
        _currentContact = _currentContact?.copy(identityNo = _user?.fullName ?: "")

        _state.value = DetailState.Success(contactModel = _currentContact)
    }
}