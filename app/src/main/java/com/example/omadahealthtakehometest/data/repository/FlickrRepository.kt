package com.example.omadahealthtakehometest.data.repository

import com.example.omadahealthtakehometest.data.model.RecentPhotoResponse
import com.example.omadahealthtakehometest.data.network.FlickrApiService
import com.example.omadahealthtakehometest.data.network.NetworkResult
import com.example.omadahealthtakehometest.ui.model.PhotoItem
import retrofit2.Response

class FlickrRepository(
    val apiService: FlickrApiService
) {
    private val apiKey = "a0222db495999c951dc33702500fdc4d" //Hide this
    private val format = "json" //Could be defaulted in service
    private val nojsoncallback = "1" //Could be defaulted in service

    private suspend fun fetchRecentPhotoResponse(method: String? = null, text: String? = null): Response<RecentPhotoResponse> {
        return apiService.getPhotoMetadata(
            method = method ?: "flickr.photos.getRecent",
            apiKey = apiKey,
            format = format,
            text = text,
            noJsonCallback = nojsoncallback,
        )
    }

    suspend fun fetchPhotosFromSearch(text: String? = null): NetworkResult<List<PhotoItem>> {
        val method = "flickr.photos.search"
        val response = fetchRecentPhotoResponse(method, text)
        val body = response.body()

        return try {
            if (response.isSuccessful && body != null) {
                val photoItems = body.photos.photo.map { metadata ->
                    PhotoItem(
                        url = "https://live.staticflickr.com/${metadata.server}/${metadata.id}_${metadata.secret}.jpg"
                    )
                }
                NetworkResult.Success(photoItems)
            } else {
                NetworkResult.Empty
            }
        } catch (e: Exception) {
            NetworkResult.Error(message = e.message.toString())
        }
    }

    suspend fun fetchRecentPhotos(): NetworkResult<List<PhotoItem>> {
        val response = fetchRecentPhotoResponse()
        val body = response.body()

       return try {
            if (response.isSuccessful && body != null) {
                val photoItems = body.photos.photo.map { metadata ->
                    PhotoItem(
                        url = "https://live.staticflickr.com/${metadata.server}/${metadata.id}_${metadata.secret}.jpg"
                    )
                }
                NetworkResult.Success(photoItems)
            } else {
                NetworkResult.Error("No photo items found")
            }
        } catch (e: Exception) {
            NetworkResult.Error(message = e.message.toString())
        }
    }
}