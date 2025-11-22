package com.example.omadahealthtakehometest.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omadahealthtakehometest.data.network.NetworkResult
import com.example.omadahealthtakehometest.data.repository.FlickrRepository
import com.example.omadahealthtakehometest.ui.model.PhotoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FlickrViewModel(
    private val repository: FlickrRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<FlickrUiState>(FlickrUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadRecentImages()
    }

    private fun loadRecentImages() {
        viewModelScope.launch {
            when (val networkResult = repository.fetchRecentPhotos()) {
                NetworkResult.Empty -> {
                    _uiState.value = FlickrUiState.Loading
                }

                is NetworkResult.Error -> {
                    _uiState.value = FlickrUiState.Error(networkResult.message)
                }

                is NetworkResult.Success -> {
                    _uiState.value = FlickrUiState.Success(networkResult.data)
                }
            }
        }
    }
}

sealed class FlickrUiState {
    data class Error(val message: String) : FlickrUiState()
    data class Success(val data: List<PhotoItem>) : FlickrUiState()
    object Loading : FlickrUiState()
}