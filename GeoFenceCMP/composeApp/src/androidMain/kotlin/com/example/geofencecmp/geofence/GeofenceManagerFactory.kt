package com.example.geofencecmp.geofence

import android.content.Context

lateinit var appContext: Context

actual object GeofenceManagerFactory {
    actual fun create(): GeofenceManager {
        return GeofenceManager(appContext)
    }
}