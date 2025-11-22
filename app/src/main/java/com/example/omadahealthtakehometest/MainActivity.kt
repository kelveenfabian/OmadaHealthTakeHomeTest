package com.example.omadahealthtakehometest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
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
                val uiState = viewModel.uiState.collectAsState()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    when (val state = uiState.value) {
                        is FlickrUiState.Error -> { Text(text = state.message)}
                        FlickrUiState.Loading -> {
                            CircularProgressIndicator()
                        }
                        is FlickrUiState.Success -> {
                            LazyColumn(
                                modifier = Modifier.padding(innerPadding)
                            ) {
                                items(state.data.size) {
                                    state.data.forEach { photoItem ->
                                        Text(
                                            text = photoItem.url
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}