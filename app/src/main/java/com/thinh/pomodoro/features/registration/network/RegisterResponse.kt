package com.thinh.pomodoro.features.registration.network

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("message") val message: String,
)