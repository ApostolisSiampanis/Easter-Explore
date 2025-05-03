package com.example.easterexplore.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.easterexplore.R
import com.example.easterexplore.presentation.event.POIEvent
import com.example.easterexplore.presentation.ui.theme.SkyBlue
import com.example.easterexplore.presentation.viewmodel.POIViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun POIListScreen(
    onAddClick: () -> Unit,
    viewModel: POIViewModel = viewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.event.collectLatest { event ->
            if (event is POIEvent.ShowError) snackbarHostState.showSnackbar(event.message)
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SkyBlue)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = SkyBlue,
                onClick = onAddClick
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add)
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            items(uiState.pois) { poi ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .background(
                            shape = MaterialTheme.shapes.medium,
                            color = Color.White
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(poi.title, style = MaterialTheme.typography.titleMedium)
                            Text("${stringResource(id = R.string.category)}: ${stringResource(id = poi.category.displayNameRes)}")
                            Text("${stringResource(id = R.string.rating)}: ${poi.rating}")
                            Text("${stringResource(id = R.string.location)}: ${poi.location.latitude}, ${poi.location.longitude}")
                            if (poi.imageUri.isNotBlank()) {
                                val model = if (poi.imageUri.startsWith("file://") || poi.imageUri.startsWith("/")) {
                                    "file://${poi.imageUri.removePrefix("file://")}"
                                } else {
                                    poi.imageUri
                                }

                                AsyncImage(
                                    model = model,
                                    contentDescription = context.getString(R.string.image),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp)
                                )
                            }


                            Text("${stringResource(id = R.string.comments)}: ${poi.comments}")
                            //TODO: add more details
                            // Make card clickable to show more details
                        }
                        IconButton(
                            onClick = { viewModel.delete(poi) }
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = stringResource(id = R.string.delete)
                            )
                        }
                    }
                }
            }
        }
    }

}