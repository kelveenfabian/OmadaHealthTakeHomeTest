package com.example.omadahealthtakehometest.data.model

data class RecentPhotoResponse(
    val photos: PhotoPage,
    val stat: String,
)

data class PhotoPage(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val total: Int,
    val photo: List<PhotoMetadata>
)

data class PhotoMetadata(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val title: String,
    val ispublic: Int,
    val isfriend: Int,
    val isfamily: Int,
)