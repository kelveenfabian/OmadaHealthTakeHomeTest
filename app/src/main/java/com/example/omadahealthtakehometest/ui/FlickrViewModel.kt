package com.example.omadahealthtakehometest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omadahealthtakehometest.data.network.NetworkResult
import com.example.omadahealthtakehometest.domain.mapper.toDomain
import com.example.omadahealthtakehometest.domain.repository.FlickrRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
@HiltViewModel
class FlickrViewModel @Inject constructor(
    private val repository: FlickrRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<FlickrUiState>(FlickrUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        searchQuery
            .debounce(300)
            .distinctUntilChanged()
            .onEach { query ->
                if (query.isEmpty()) {
                    onFlickrUserEvent(FlickrUiEvent.LoadRecentImages)
                } else {
                    onFlickrUserEvent(FlickrUiEvent.SearchImages)
                }
            }
            .launchIn(viewModelScope)
    }

    fun onFlickrUserEvent(event: FlickrUiEvent) {
        when (event) {
            FlickrUiEvent.LoadRecentImages -> {
                loadRecentImages()
            }

            is FlickrUiEvent.UpdateSearchQuery -> {
                _searchQuery.value = event.text
            }

            FlickrUiEvent.SearchImages -> {
                searchImages(_searchQuery.value)
            }
        }
    }

    private fun searchImages(text: String) {
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