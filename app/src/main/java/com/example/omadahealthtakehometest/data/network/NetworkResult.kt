package com.example.omadahealthtakehometest.data.network

sealed interface NetworkResult<out T> {
    data class Error(val message: String): NetworkResult<Nothing>
    data class Success<out T>(val data: T): NetworkResult<T>
    object Empty : NetworkResult<Nothing>
}