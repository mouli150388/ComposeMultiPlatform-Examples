package com.example.cmpphonecall

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform