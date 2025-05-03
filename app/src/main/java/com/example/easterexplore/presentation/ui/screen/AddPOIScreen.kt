package com.example.easterexplore.presentation.ui.screen

import android.content.pm.PackageManager
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
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import android.Manifest
import android.content.Context
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.asImageBitmap
import com.example.easterexplore.domain.model.Category
import com.example.easterexplore.domain.model.Location
import com.example.easterexplore.domain.model.PointOfInterest
import com.example.easterexplore.presentation.event.POIEvent
import com.example.easterexplore.presentation.ui.component.DropdownMenuCategory
import com.example.easterexplore.presentation.viewmodel.POIViewModel
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

@Composable
fun AddPOIScreen(
    onSave: () -> Unit,
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
                val path = saveBitmapToInternalStorage(context, bitmap)
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
                        snackbarHostState.showSnackbar("Camera permission denied")
                    }
                }
            }
            "getLocation" -> {
                if (locationGranted) {
                    fetchLocation(context) { fetchedLocation ->
                        location = fetchedLocation
                        viewModel.add(
                            PointOfInterest(
                                title = title,
                                category = selectedCategory,
                                rating = rating,
                                location = fetchedLocation,
                                imageUri = imageUri?.toString() ?: "",
                                comments = comments
                            )
                        )
                    }
                } else {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Location permission denied")
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
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            DropdownMenuCategory(
                selected = selectedCategory,
                onSelected = { selectedCategory = it }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = rating.toString(),
                onValueChange = { rating = it.toFloatOrNull() ?: 0f },
                label = { Text("Rating") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            imageBitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.height(200.dp)
                )
            }

            Button(
                onClick = {
                    pendingAction = "takePhoto"
                    permissionLauncher.launch(arrayOf(Manifest.permission.CAMERA))
                }
            ) {
                Text("Take photo")
            }

            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = comments,
                onValueChange = { comments = it },
                label = { Text("Comments") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    pendingAction = "getLocation"
                    permissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
                }
            ) {
                Text("Save")
            }
        }
    }
}

private fun fetchLocation(context: Context, onLocationFetched: (Location) -> Unit) {
    val fusedClient = LocationServices.getFusedLocationProviderClient(context)
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED
    ) {
        fusedClient.lastLocation.addOnSuccessListener {
            if (it != null) {
                onLocationFetched(Location(it.latitude, it.longitude))
            }
        }
    }
}

private fun saveBitmapToInternalStorage(context: Context, bitmap: Bitmap): String {
    val filename = "poi_${System.currentTimeMillis()}.jpg"
    val file = File(context.filesDir, filename)
    FileOutputStream(file).use { out ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
    }
    return file.absolutePath
}