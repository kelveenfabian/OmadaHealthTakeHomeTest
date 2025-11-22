package com.example.omadahealthtakehometest.data.repository

import com.example.omadahealthtakehometest.data.model.RecentPhotoResponse
import com.example.omadahealthtakehometest.data.network.FlickrApiService
import com.example.omadahealthtakehometest.data.network.NetworkResult
import com.example.omadahealthtakehometest.data.network.networkResultHandler
import com.example.omadahealthtakehometest.domain.repository.FlickrRepository
import com.example.omadahealthtakehometest.BuildConfig

class FlickrRepositoryImpl(
    val apiService: FlickrApiService
) : FlickrRepository {
    private val format = "json" //Could be defaulted in service
    private val nojsoncallback = "1" //Could be defaulted in service

    override suspend fun fetchPhotosFromSearch(text: String?): NetworkResult<RecentPhotoResponse> {
        val method = "flickr.photos.search"
        return fetchRecentPhotoResponseNetworkResult(method, text)
    }

    override suspend fun fetchRecentPhotos(): NetworkResult<RecentPhotoResponse> {
       return fetchRecentPhotoResponseNetworkResult()
    }

    private suspend fun fetchRecentPhotoResponseNetworkResult(method: String? = null, text: String? = null): NetworkResult<RecentPhotoResponse> {
        return networkResultHandler {
            apiService.getPhotoMetadata(
                method = method ?: "flickr.photos.getRecent",
                apiKey = BuildConfig.API_KEY,
                format = format,
                text = text,
                noJsonCallback = nojsoncallback,
            )
        }
    }
}