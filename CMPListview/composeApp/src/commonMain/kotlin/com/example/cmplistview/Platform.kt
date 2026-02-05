package com.example.cmplistview

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform