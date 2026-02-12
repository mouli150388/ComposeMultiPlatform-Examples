package com.example.geofencecmp.geofence

expect class GeofenceManager {
    fun addGeofence(id: String, lat: Double, lng: Double, radius: Float)
    fun removeGeofence(id: String)
}