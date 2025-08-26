package com.example.medicine_reminder.retrofit

import com.example.medicine_reminder.api.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL =
        "https://probable-humbly-mako.ngrok-free.app/" // for emulator; change for real device
    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)   // connection timeout
        .readTimeout(60, TimeUnit.SECONDS)      // server response timeout
        .writeTimeout(10, TimeUnit.SECONDS)     // request send timeout
        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }
}
