package com.example.cmpdatepicker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform