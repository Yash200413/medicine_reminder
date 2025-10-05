package com.example.medicine_reminder.ui.login

data class LoginUiState(
    val loading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null
)
