package com.example.geofencecmp

import android.app.Application
import com.example.geofencecmp.geofence.appContext

class GeoFenceCMPApp: Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}