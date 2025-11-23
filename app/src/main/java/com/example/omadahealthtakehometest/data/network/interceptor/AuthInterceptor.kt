package com.example.omadahealthtakehometest.data.network.interceptor

import com.example.omadahealthtakehometest.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val url = originalUrl.newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .addQueryParameter("format", "json")
            .addQueryParameter("nojsoncallback", "1")
            .build()

        val request = originalRequest.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}