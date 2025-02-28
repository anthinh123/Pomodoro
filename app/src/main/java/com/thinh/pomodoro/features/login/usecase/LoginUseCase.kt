package com.thinh.pomodoro.features.login.usecase

import com.thinh.pomodoro.retrofit.ApiHandler
import com.thinh.pomodoro.retrofit.NetworkResult
import com.thinh.pomodoro.features.login.network.AuthenticationApi
import com.thinh.pomodoro.features.login.network.LoginRequest
import com.thinh.pomodoro.features.login.network.LoginResponse

class LoginUseCase(
    private val authenticationApi: AuthenticationApi,
) : ApiHandler {

    suspend fun execute(username: String, password: String): NetworkResult<LoginResponse> {
        val request = LoginRequest(userName = username, password = password)
        return handleApi {
            authenticationApi.login(request = request)
        }
    }
}