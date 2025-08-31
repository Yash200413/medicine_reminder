package com.example.medicine_reminder.data.repository

import com.example.medicine_reminder.data.api.ApiService
import com.example.medicine_reminder.model.ChangePasswordRequest
import com.example.medicine_reminder.model.ChangePasswordResponse
import javax.inject.Inject

class ChangePasswordRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun changePassword(request: ChangePasswordRequest): Result<ChangePasswordResponse> {
        return try {
            val response = apiService.changePassword(request)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Change password failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
