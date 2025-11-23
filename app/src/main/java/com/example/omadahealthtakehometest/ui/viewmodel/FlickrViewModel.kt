package com.example.omadahealthtakehometest.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omadahealthtakehometest.data.network.NetworkResult
import com.example.omadahealthtakehometest.domain.mapper.toDomain
import com.example.omadahealthtakehometest.domain.model.PhotoItem
import com.example.omadahealthtakehometest.domain.repository.FlickrRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class FlickrViewModel @Inject constructor(
    private val repository: FlickrRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<FlickrUiState>(FlickrUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun updateUi(text: String) {
        viewModelScope.launch {
            when (val networkResult = repository.fetchPhotosFromSearch(text = text)) {
                NetworkResult.Empty -> {
                    _uiState.value = FlickrUiState.Loading
                }

                is NetworkResult.Error -> {
                    _uiState.value = FlickrUiState.Error(networkResult.message)
                }

                is NetworkResult.Success -> {
                    val photoItems = networkResult.data.toDomain()
                    _uiState.value = FlickrUiState.Success(photoItems)
                }
            }
        }
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
                    val photoItems = networkResult.data.toDomain()
                    _uiState.value = FlickrUiState.Success(photoItems)
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