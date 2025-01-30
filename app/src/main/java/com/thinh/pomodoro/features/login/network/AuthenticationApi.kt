package com.thinh.pomodoro.features.login.network

import com.thinh.pomodoro.features.registration.network.RegisterRequest
import com.thinh.pomodoro.features.registration.network.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationApi {
    @POST("/api/v1/auth/signin")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("/api/v1/auth/signup")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

}