package com.example.omadahealthtakehometest.data.repository

import com.example.omadahealthtakehometest.data.model.RecentPhotoResponse
import com.example.omadahealthtakehometest.data.network.FlickrApiService
import com.example.omadahealthtakehometest.data.network.NetworkResult
import com.example.omadahealthtakehometest.data.network.networkResultHandler
import com.example.omadahealthtakehometest.domain.repository.FlickrRepository
import jakarta.inject.Inject

class FlickrRepositoryImpl @Inject constructor(
    val apiService: FlickrApiService
) : FlickrRepository {

    override suspend fun fetchPhotosFromSearch(text: String?): NetworkResult<RecentPhotoResponse> {
        val method = "flickr.photos.search"
        return fetchRecentPhotoResponseNetworkResult(method, text)
    }

    override suspend fun fetchRecentPhotos(): NetworkResult<RecentPhotoResponse> {
        return fetchRecentPhotoResponseNetworkResult()
    }

    private suspend fun fetchRecentPhotoResponseNetworkResult(
        method: String? = null,
        text: String? = null
    ): NetworkResult<RecentPhotoResponse> {
        return networkResultHandler {
            apiService.getPhotoMetadata(
                method = method ?: "flickr.photos.getRecent",
                text = text,
            )
        }
    }
}