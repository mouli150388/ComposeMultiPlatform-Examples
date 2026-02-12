package com.example.geofencecmp.geofence

actual object GeofenceManagerFactory {
    actual fun create(): GeofenceManager {
        return GeofenceManager()
    }
}