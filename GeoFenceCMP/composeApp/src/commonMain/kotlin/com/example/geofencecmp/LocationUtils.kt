package com.example.geofencecmp

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

fun distanceBetween(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Float {
    val earthRadius = 6371000 // meters
    val dLat = (lat2 - lat1) * (3.14159 / 180.0)
    val dLon = (lon2 - lon1) * (3.14159 / 180.0)
    val a = sin(dLat / 2) * sin(dLat / 2) +
            cos(lat1 * (3.14159 / 180.0)) * cos(lat2 * (3.14159 / 180.0)) *
            sin(dLon / 2) * sin(dLon / 2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return (earthRadius * c).toFloat()
}