package com.thinh.pomodoro.features.login.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationApi {
    @POST("/api/v1/auth/signin")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}