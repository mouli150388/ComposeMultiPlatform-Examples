package com.rrtutors.cmptravelapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform