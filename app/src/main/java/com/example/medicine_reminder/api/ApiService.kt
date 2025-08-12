package com.example.medicine_reminder.api

import com.example.medicine_reminder.model.LoginRequest
import com.example.medicine_reminder.model.LoginResponse
import com.example.medicine_reminder.model.SignUpRequest
import com.example.medicine_reminder.model.SignUpResponse
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
}
