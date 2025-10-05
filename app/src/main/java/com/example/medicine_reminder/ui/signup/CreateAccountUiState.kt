package com.example.medicine_reminder.ui.signup

data class CreateAccountUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)

