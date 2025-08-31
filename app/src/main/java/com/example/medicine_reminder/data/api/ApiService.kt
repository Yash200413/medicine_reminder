package com.example.medicine_reminder.data.api

import com.example.medicine_reminder.model.ChangePasswordRequest
import com.example.medicine_reminder.model.ChangePasswordResponse
import com.example.medicine_reminder.model.OtpRequest
import com.example.medicine_reminder.model.GoogleLoginRequest
import com.example.medicine_reminder.model.LoginRequest
import com.example.medicine_reminder.model.LoginResponse
import com.example.medicine_reminder.model.OtpResponse
import com.example.medicine_reminder.model.SignUpRequest
import com.example.medicine_reminder.model.SignUpResponse
import com.example.medicine_reminder.model.VerifyOtp
import com.example.medicine_reminder.model.VerifyOtpResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("/api/auth/signup")
    suspend fun signUp(@Body request: SignUpRequest): Response<SignUpResponse>

    @POST("/api/auth/google")
    suspend fun googleLogin(@Body request: GoogleLoginRequest): Response<LoginResponse>

    @POST("/api/auth/send")
    suspend fun sendOtp(@Body request: OtpRequest): Response<OtpResponse>

    @POST("/api/auth/verify")
    suspend fun verifyOtp(@Body request: VerifyOtp): Response<VerifyOtpResponse>

    @POST("/api/auth/change-password")
    suspend fun changePassword(@Body request: ChangePasswordRequest): Response<ChangePasswordResponse>

    @GET("/api/auth/validate")
    suspend fun validateToken(@Header("Authorization") authHeader: String): Response<Void>
}
