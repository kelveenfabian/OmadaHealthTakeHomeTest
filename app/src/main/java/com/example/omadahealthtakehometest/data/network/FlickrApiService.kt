package com.example.omadahealthtakehometest.data.network

import com.example.omadahealthtakehometest.data.model.RecentPhotoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApiService {

    @GET("/services/rest/")
    suspend fun getPhotoMetadata(
        @Query("method") method: String? = null,
        @Query("api_key") apiKey: String? = null,
        @Query("format") format: String? = null,
        @Query("text") text: String? = null,
        @Query("nojsoncallback") noJsonCallback: String? = null,
    ) : Response<RecentPhotoResponse>
}