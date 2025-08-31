package com.example.medicine_reminder.data.repository

import com.example.medicine_reminder.data.api.ApiService
import com.example.medicine_reminder.model.*
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun login(request: LoginRequest): Response<LoginResponse> =
        apiService.login(request)

    suspend fun signUp(request: SignUpRequest): Response<SignUpResponse> =
        apiService.signUp(request)

    suspend fun googleLogin(request: GoogleLoginRequest): Response<LoginResponse> =
        apiService.googleLogin(request)

    suspend fun sendOtp(request: OtpRequest): Response<OtpResponse> =
        apiService.sendOtp(request)

    suspend fun verifyOtp(request: VerifyOtp): Response<VerifyOtpResponse> =
        apiService.verifyOtp(request)

    suspend fun changePassword(request: ChangePasswordRequest): Response<ChangePasswordResponse> =
        apiService.changePassword(request)

    suspend fun validateToken(token: String): Response<Void> =
        apiService.validateToken(token)
}
