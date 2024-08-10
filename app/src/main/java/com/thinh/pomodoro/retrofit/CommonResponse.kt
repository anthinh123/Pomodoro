package com.mtcld.repaircheck.core.retrofit

import com.google.gson.annotations.SerializedName

data class CommonResponse(
    @SerializedName("status") val status: Status,
    @SerializedName("step") val step: String,
)

data class Status(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String
)