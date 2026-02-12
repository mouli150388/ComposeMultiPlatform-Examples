package com.example.geofencecmp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.kCLLocationAccuracyBest

actual class LocationService {

    private val locationManager = CLLocationManager()

    init {
        locationManager.requestWhenInUseAuthorization()
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
    }

    @OptIn(ExperimentalForeignApi::class)
    @Composable
    actual fun getCurrentLocation(): State<LatLng?> {
        return produceState<LatLng?>(initialValue = null) {
            val loc = locationManager.location
            if (loc != null) {
                value = loc.coordinate.useContents { LatLng(this.latitude, this.longitude) }
            }
        }
    }
}