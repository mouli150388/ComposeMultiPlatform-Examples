package com.example.geofencecmp

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import com.example.geofencecmp.geofence.appContext
import com.google.android.gms.location.LocationServices

actual class LocationService {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(appContext)

    @SuppressLint("MissingPermission")
    @Composable
    actual fun getCurrentLocation(): State<LatLng?> {
        return produceState<LatLng?>(initialValue = null) {
            fusedLocationClient.lastLocation.addOnSuccessListener { loc ->
                if (loc != null) {
                    value = LatLng(loc.latitude, loc.longitude)
                }
            }
        }
    }
}