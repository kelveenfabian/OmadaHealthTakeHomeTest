package com.example.omadahealthtakehometest.ui

sealed class FlickrUiEvent {
    data class UpdateSearchQuery(val text: String) : FlickrUiEvent()
    object LoadRecentImages : FlickrUiEvent()

    object SearchImages : FlickrUiEvent()
}