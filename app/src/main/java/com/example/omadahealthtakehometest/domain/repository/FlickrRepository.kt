package com.example.omadahealthtakehometest.domain.repository

import com.example.omadahealthtakehometest.data.model.RecentPhotoResponse
import com.example.omadahealthtakehometest.data.network.NetworkResult

interface FlickrRepository {

    suspend fun fetchPhotosFromSearch(text: String?): NetworkResult<RecentPhotoResponse>

    suspend fun fetchRecentPhotos(): NetworkResult<RecentPhotoResponse>
}