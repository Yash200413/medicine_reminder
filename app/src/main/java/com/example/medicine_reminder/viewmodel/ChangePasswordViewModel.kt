package com.example.medicine_reminder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicine_reminder.data.repository.ChangePasswordRepository
import com.example.medicine_reminder.model.ChangePasswordRequest
import com.example.medicine_reminder.model.ChangePasswordResponse
import com.example.medicine_reminder.ui.forgotpassword.ChangePasswordUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val repository: ChangePasswordRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChangePasswordUiState())
    val uiState: StateFlow<ChangePasswordUiState> = _uiState

    fun changePassword(email: String, newPassword: String, confirmPassword: String) {
        if (newPassword != confirmPassword) {
            _uiState.value = ChangePasswordUiState(error = "Passwords do not match")
            return
        }

        _uiState.value = ChangePasswordUiState(loading = true)

        val request = ChangePasswordRequest(email, newPassword)
        viewModelScope.launch {
            // Call repository which returns Result<ChangePasswordResponse>
            val result: Result<ChangePasswordResponse> = repository.changePassword(request)

            result
                .onSuccess { response ->
                    if (response.message) {  // Now 'message' is accessible
                        _uiState.value = ChangePasswordUiState(success = true)
                    } else {
                        _uiState.value = ChangePasswordUiState(error = "Password change failed")
                    }
                }
                .onFailure { throwable ->
                    _uiState.value = ChangePasswordUiState(error = throwable.message ?: "Unknown error")
                }
        }
    }


    fun resetState() {
        _uiState.value = ChangePasswordUiState()
    }
}
