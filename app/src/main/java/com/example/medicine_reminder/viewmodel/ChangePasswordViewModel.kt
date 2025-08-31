package com.example.medicine_reminder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicine_reminder.data.repository.ChangePasswordRepository
import com.example.medicine_reminder.model.ChangePasswordRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val repository: ChangePasswordRepository
) : ViewModel() {

    fun changePassword(
        email: String,
        newPassword: String,
        confirmPassword: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (newPassword != confirmPassword) {
            onError("Passwords do not match")
            return
        }

        val request = ChangePasswordRequest(email, newPassword)

        viewModelScope.launch {
            val result = repository.changePassword(request)
            result.onSuccess {
                if (it.message) { // assuming message is Boolean
                    onSuccess()
                } else {
                    onError("Password change failed")
                }
            }.onFailure {
                onError(it.message ?: "Unknown error")
            }
        }
    }
}
