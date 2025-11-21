package com.example.omadahealthtakehometest.data.network

import com.example.omadahealthtakehometest.data.model.RecentPhotoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApiService {

    @GET("/services/rest/")
    suspend fun getRecent(
        @Query("method") method: String,
        @Query("api_key") apiKey: String,
        @Query("format") format: String,
        @Query("nojsoncallback") noJsonCallback: String,
    ) : Response<RecentPhotoResponse>
}