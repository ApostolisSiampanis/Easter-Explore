package com.example.easterexplore.presentation.ui.screen

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import android.Manifest
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import com.example.easterexplore.R
import com.example.easterexplore.domain.model.Category
import com.example.easterexplore.domain.model.Location
import com.example.easterexplore.domain.model.PointOfInterest
import com.example.easterexplore.presentation.event.POIEvent
import com.example.easterexplore.presentation.ui.component.DropdownMenuCategory
import com.example.easterexplore.presentation.ui.theme.SkyBlue
import com.example.easterexplore.presentation.utils.FileHelper
import com.example.easterexplore.presentation.utils.LocationHelper
import com.example.easterexplore.presentation.viewmodel.POIViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPOIScreen(
    onSave: () -> Unit,
    onBackPressed: () -> Unit,
    viewModel: POIViewModel = viewModel()
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    var title by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(Category.PARK) }
    var rating by remember { mutableStateOf(0f) }
    var comments by remember { mutableStateOf("") }
    var location by remember { mutableStateOf<Location?>(null) }
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    var pendingAction by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap ->
            if (bitmap != null) {
                imageBitmap = bitmap
                val path = FileHelper.saveBitmapToInternalStorage(context, bitmap)
                imageUri = Uri.fromFile(File(path))
            }
        }
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val cameraGranted = permissions[Manifest.permission.CAMERA] ?: false
        val locationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false

        when (pendingAction) {
            "takePhoto" -> {
                if (cameraGranted) cameraLauncher.launch()
                else {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(context.getString(R.string.camera_permission_denied))
                    }
                }
            }
            "getLocation" -> {
                if (locationGranted) {
                    LocationHelper.fetchLocation(context) { fetchedLocation ->
                        location = fetchedLocation
                        viewModel.add(
                            PointOfInterest(
                                title = title,
                                category = selectedCategory,
                                rating = rating,
                                location = fetchedLocation!!,
                                imageUri = imageUri?.toString() ?: "",
                                comments = comments
                            )
                        )
                    }
                } else {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(context.getString(R.string.location_permission_denied))
                    }
                }
            }
        }

        pendingAction = null
    }

    LaunchedEffect(Unit) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is POIEvent.SavedSuccessfully -> onSave()
                is POIEvent.ShowError -> snackbarHostState.showSnackbar(event.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.add_new_place)) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SkyBlue),
                navigationIcon = {
                    IconButton(
                        onClick = onBackPressed
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(stringResource(id = R.string.title)) }
            )
            Spacer(modifier = Modifier.height(8.dp))
            DropdownMenuCategory(
                context = context,
                selected = selectedCategory,
                onSelected = { selectedCategory = it }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = rating.toString(),
                onValueChange = { rating = it.toFloatOrNull() ?: 0f },
                label = { Text(stringResource(id = R.string.rating)) }
            )
            Spacer(modifier = Modifier.height(8.dp))

            imageBitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = context.getString(R.string.image),
                    modifier = Modifier.height(200.dp)
                )
            }

            Button(
                onClick = {
                    pendingAction = "takePhoto"
                    permissionLauncher.launch(arrayOf(Manifest.permission.CAMERA))
                }
            ) {
                Text(stringResource(id = R.string.take_photo))
            }

            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = comments,
                onValueChange = { comments = it },
                label = { Text(stringResource(id = R.string.comments)) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    pendingAction = "getLocation"
                    permissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
                }
            ) {
                Text(stringResource(id = R.string.save))
            }
        }
    }
}