package com.example.omadahealthtakehometest.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object NetworkClient {
    private const val BASE_URL = "https://www.flickr.com"
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val flickrService: FlickrApiService by lazy { retrofit.create(FlickrApiService::class.java) }
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