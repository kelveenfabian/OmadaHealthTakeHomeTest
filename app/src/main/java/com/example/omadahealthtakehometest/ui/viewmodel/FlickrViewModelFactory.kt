package com.example.omadahealthtakehometest.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.omadahealthtakehometest.domain.repository.FlickrRepository
import jakarta.inject.Inject

@Suppress("UNCHECKED_CAST")
class FlickrViewModelFactory @Inject constructor(
    private val flickrRepository: FlickrRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlickrViewModel::class.java)) {
            return FlickrViewModel(flickrRepository) as T
        }
        throw IllegalStateException("Unknown View Model Found")
    }
}