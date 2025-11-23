package com.example.omadahealthtakehometest.data.network

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

sealed interface NetworkResult<out T> {
    data class Error(val exception: Throwable, val message: String): NetworkResult<Nothing>
    data class Success<out T>(val data: T): NetworkResult<T>
    object Empty : NetworkResult<Nothing>
}

suspend fun <T> networkResultHandler(apiCall: suspend () -> Response<T>): NetworkResult<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful && response.body() != null) {
            NetworkResult.Success(response.body()!!)
        } else {
            val errorBody = response.errorBody().toString()
            NetworkResult.Error(HttpException(response), errorBody)
        }
    } catch (e: IOException) {
        NetworkResult.Error(e, "Network error: Check your internet connection")
    } catch (e: Exception) {
        NetworkResult.Error(e, "An unexpected error occurred")
    }
}