package com.thinh.pomodoro.features.registration.usecase

import com.thinh.pomodoro.retrofit.NetworkResult
import com.thinh.pomodoro.features.login.network.AuthenticationApi
import com.thinh.pomodoro.features.registration.network.RegisterRequest
import com.thinh.pomodoro.features.registration.network.RegisterResponse
import com.thinh.pomodoro.retrofit.ApiHandler

class RegistrationUseCase(
    private val authenticationApi: AuthenticationApi,
) : ApiHandler {

    suspend fun execute(userName: String, email: String, password: String): NetworkResult<RegisterResponse> {
        val request = RegisterRequest(userName, email, password)
        return handleApi {
            authenticationApi.register(request)
        }
    }
}