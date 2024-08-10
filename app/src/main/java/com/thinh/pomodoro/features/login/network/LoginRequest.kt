package com.thinh.pomodoro.features.login.network

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("userName") val userName: String,
    @SerializedName("password") val password: String,
)