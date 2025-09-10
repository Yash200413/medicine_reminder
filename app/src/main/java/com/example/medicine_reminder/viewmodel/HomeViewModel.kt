package com.example.medicine_reminder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicine_reminder.data.api.ApiService
import com.example.medicine_reminder.data.local.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val api: ApiService,
    private val tokenManager: TokenManager
) : ViewModel() {

    fun logout(onLoggedOut: () -> Unit) {
        viewModelScope.launch {
            tokenManager.clearToken()
            onLoggedOut()
        }

    }
    fun formatTime(time: Long?): String {
        return time?.let {
            SimpleDateFormat("hh:mm a", Locale.getDefault()).format(it)
        } ?: ""
    }
    // Later you can add:
    // fun fetchMedicines(...)
    // fun deleteMedicine(...)
    // fun updateMedicine(...)
}
