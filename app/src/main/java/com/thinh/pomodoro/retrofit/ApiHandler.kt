package com.mtcld.repaircheck.core.retrofit

import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response

interface ApiHandler {
    suspend fun <T : Any> handleApi(
        execute: suspend () -> Response<T>
    ): NetworkResult<T> {
        return try {
            val response = execute()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                NetworkResult.Success(response.code(), body)
            } else {
                NetworkResult.Error(
                    code = response.code(),
                    errorMsg = parseError(response)
                )
            }
        } catch (e: HttpException) {
            NetworkResult.Error(e.code(), e.message())
        } catch (e: Throwable) {
            NetworkResult.Exception(e)
        }
    }
}

fun parseError(response: Response<*>): String {
    return try {
        val jObjError = JSONObject(response.errorBody()!!.string())
        jObjError.getJSONObject("status").getString("message")
    } catch (e: Exception) {
        "Something went wrong"
    }
}

