package com.example.omadahealthtakehometest.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.omadahealthtakehometest.data.network.NetworkClient
import com.example.omadahealthtakehometest.data.repository.FlickrRepository

@Suppress("UNCHECKED_CAST")
class FlickrViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlickrViewModel::class.java)) {
            return FlickrViewModel(
                FlickrRepository(
                    NetworkClient.flickrService
                )
            ) as T
        }
        throw IllegalStateException("Unknown View Model Found")
    }
}