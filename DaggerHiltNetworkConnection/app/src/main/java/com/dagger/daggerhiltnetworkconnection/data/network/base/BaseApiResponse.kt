package com.dagger.daggerhiltnetworkconnection.data.network.base

import com.dagger.daggerhiltnetworkconnection.data.network.status.ApiResponse
import retrofit2.Response
import java.lang.Exception

abstract class BaseApiResponse {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ApiResponse<T> {
        try {
            val response = apiCall()
            if(response.isSuccessful) {
                val body = response.body()
                val code = response.code()
                body?.let {
                    return ApiResponse.Success(body, code)
                }
            }
            error("${response.code()} ${response.message()}")
        }catch (e: Exception) {
            error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String, errorCode: Int): ApiResponse<T> =
        ApiResponse.ApiError("Api call failed :: $errorMessage", errorCode)
}