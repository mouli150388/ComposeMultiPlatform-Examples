package com.example.geofencecmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform