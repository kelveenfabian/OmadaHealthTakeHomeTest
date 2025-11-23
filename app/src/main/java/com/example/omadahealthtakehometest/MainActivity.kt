package com.example.omadahealthtakehometest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.omadahealthtakehometest.domain.model.PhotoItem
import com.example.omadahealthtakehometest.ui.FlickrUiEvent
import com.example.omadahealthtakehometest.ui.FlickrUiState
import com.example.omadahealthtakehometest.ui.FlickrViewModel
import com.example.omadahealthtakehometest.ui.theme.OmadaHealthTakeHomeTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OmadaHealthTakeHomeTestTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray)
                ) { innerPadding ->
                    FlickrScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun FlickrScreen(viewModel: FlickrViewModel = hiltViewModel(), modifier: Modifier) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    when (val state = uiState) {
        is FlickrUiState.Error -> ErrorContent(modifier, state.message)


        FlickrUiState.Loading -> LoadingContent(modifier)


        is FlickrUiState.Success -> SuccessContent(
            data = state.data,
            modifier = modifier,
            viewModel = viewModel,
            searchQuery = searchQuery,
        )
    }
}

@Composable
private fun ErrorContent(modifier: Modifier, message: String) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Error: $message")
    }
}

@Composable
private fun LoadingContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun SuccessContent(
    data: List<PhotoItem>,
    modifier: Modifier = Modifier,
    viewModel: FlickrViewModel,
    searchQuery: String
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TextField(
            value = searchQuery,
            onValueChange = {
                viewModel.onFlickrUserEvent(FlickrUiEvent.UpdateSearchQuery(it))
            },
            label = { Text(text = "Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            singleLine = true,
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp)
        ) {
            items(data) { photoItem ->
                FlickrPhotoItem(photoItem = photoItem)
            }
        }
    }
}

@Composable
private fun FlickrPhotoItem(photoItem: PhotoItem) {
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(photoItem.url)
                .crossfade(true)
                .build(),
            contentDescription = "Image from Flickr",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}