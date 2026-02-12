package com.example.geofencecmp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

expect class LocationService {
    @Composable
    fun getCurrentLocation(): State<LatLng?>
}

data class LatLng(val latitude: Double, val longitude: Double)
