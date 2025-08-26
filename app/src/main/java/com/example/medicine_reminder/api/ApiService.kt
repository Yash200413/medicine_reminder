package com.example.medicine_reminder.api

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
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/api/auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("/api/auth/signup")
    fun signUp(@Body request: SignUpRequest): Call<SignUpResponse>

    @POST("/api/auth/google")
    fun googleLogin(@Body request: GoogleLoginRequest): Call<LoginResponse>

    @POST("/api/auth/send")
    fun sendOtp(@Body request: OtpRequest): Call<OtpResponse>

    @POST("/api/auth/verify")
    fun verifyOtp(@Body request: VerifyOtp): Call<VerifyOtpResponse>

    @POST("/api/auth/change-password")
    fun changePassword(@Body request: ChangePasswordRequest): Call<ChangePasswordResponse>
}
