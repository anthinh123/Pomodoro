package com.thinh.pomodoro.features.login.network

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("accessToken")
    val token: String,
)
