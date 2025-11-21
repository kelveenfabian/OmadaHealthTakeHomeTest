package com.example.omadahealthtakehometest.data.repository

import com.example.omadahealthtakehometest.data.network.FlickrApiService

class FlickrRepository(
    val apiService: FlickrApiService
) {
    private val apiKey = "a0222db495999c951dc33702500fdc4d"
    private val format = "json"
    private val nojsoncallback = "1"
    suspend fun fetchRecentPhotos() {
        val method = "flickr.photos.getRecent"

        apiService.getRecent(
            method = method,
            apiKey = apiKey,
            format = format,
            noJsonCallback = nojsoncallback,
        )
    }
}