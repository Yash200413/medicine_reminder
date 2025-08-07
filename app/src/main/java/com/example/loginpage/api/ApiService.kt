package com.example.loginpage.api

import com.example.loginpage.model.LoginRequest
import com.example.loginpage.model.LoginResponse
import com.example.loginpage.model.SignUpRequest
import com.example.loginpage.model.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/api/auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
    @POST("/api/auth/signup")
fun signUp(@Body request: SignUpRequest): Call<SignUpResponse>
}
