package com.example.easterexplore.presentation.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.example.easterexplore.domain.model.Location
import com.google.android.gms.location.LocationServices

/**
 * A utility object for location-related operations.
 */
object LocationHelper {

    /**
     * Fetches the last known location of the device.
     *
     * @param context The context of the application.
     * @param onLocationFetched Callback function to be invoked with the fetched location.
     */
    fun fetchLocation(context: Context, onLocationFetched: (Location?) -> Unit) {
        val fusedClient = LocationServices.getFusedLocationProviderClient(context)
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            fusedClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    onLocationFetched(Location(location.latitude, location.longitude))
                } else {
                    onLocationFetched(null)
                }
            }
        } else {
            onLocationFetched(null)
        }
    }

}