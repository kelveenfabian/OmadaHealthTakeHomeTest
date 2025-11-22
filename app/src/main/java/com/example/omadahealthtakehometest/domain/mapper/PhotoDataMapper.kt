package com.example.omadahealthtakehometest.domain.mapper

import com.example.omadahealthtakehometest.data.model.RecentPhotoResponse
import com.example.omadahealthtakehometest.domain.model.PhotoItem

fun RecentPhotoResponse.toDomain(): List<PhotoItem> {
    return this.photos.photo.map { metadata ->
        PhotoItem(
            url = "https://live.staticflickr.com/${metadata.server}/${metadata.id}_${metadata.secret}.jpg"
        )
    }
}