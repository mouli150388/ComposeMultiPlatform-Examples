package com.example.geofencecmp.geofence

import platform.CoreLocation.CLLocationManager

actual class GeofenceManager {
    private val locationManager = CLLocationManager()
    actual fun addGeofence(id: String, lat: Double, lng: Double, radius: Float) {
        // Not implemented for iOS
    }

    actual fun removeGeofence(id: String) {
        // Not implemented for iOS
    }
}