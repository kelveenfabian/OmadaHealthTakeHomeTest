package com.example.omadahealthtakehometest.ui

import com.example.omadahealthtakehometest.domain.model.PhotoItem

sealed class FlickrUiState {
    data class Error(val message: String) : FlickrUiState()
    data class Success(val data: List<PhotoItem>) : FlickrUiState()
    object Loading : FlickrUiState()
}