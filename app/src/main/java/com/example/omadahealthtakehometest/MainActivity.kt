package com.example.omadahealthtakehometest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.omadahealthtakehometest.ui.theme.OmadaHealthTakeHomeTestTheme
import com.example.omadahealthtakehometest.ui.viewmodel.FlickrUiState
import com.example.omadahealthtakehometest.ui.viewmodel.FlickrViewModel
import com.example.omadahealthtakehometest.ui.viewmodel.FlickrViewModelFactory

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: FlickrViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = FlickrViewModelFactory().create(FlickrViewModel::class.java)
        enableEdgeToEdge()
        setContent {
            OmadaHealthTakeHomeTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize().background(Color.Gray)) { innerPadding ->
                    FlickrScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlickrScreen(viewModel: FlickrViewModel, modifier: Modifier) {
    val uiState = viewModel.uiState.collectAsState()
    when (val state = uiState.value) {
        is FlickrUiState.Error -> {
            Text(text = state.message)
        }

        FlickrUiState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is FlickrUiState.Success -> {
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                var expanded by rememberSaveable { mutableStateOf(false) }

                SearchBar(
                    modifier = Modifier.align(Alignment.TopCenter),
                    inputField = {
                        SearchBarDefaults.InputField(
                            query = "Search",
                            onQueryChange = { "Yo" },
                            onSearch = {
                                {"yo"} //Add MainEventUi to VM
                                expanded = false
                            },
                            expanded = expanded,
                            onExpandedChange = {expanded = it},
                            placeholder = { Text("Search") }
                        )
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                ) {
                    LazyVerticalGrid(
                        modifier = modifier.fillMaxSize(),
                        columns = GridCells.Adaptive(minSize = 128.dp)
                    ) {
                        items(state.data) { photoItem ->

                            Box(
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .clip(RectangleShape)
                                    .padding(4.dp)
                                ,
                                contentAlignment = Alignment.Center
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(photoItem.url)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "Image from Flickr",
                                    contentScale = ContentScale.Crop,
                                )
                            }
                        }
                    }
                }

                LazyVerticalGrid(
                    modifier = modifier.fillMaxSize(),
                    columns = GridCells.Adaptive(minSize = 128.dp)
                ) {
                    items(state.data) { photoItem ->

                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(RectangleShape)
                                .padding(4.dp)
                            ,
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(photoItem.url)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Image from Flickr",
                                contentScale = ContentScale.Crop,
                            )
                        }
                    }
                }
            }

        }
    }
}