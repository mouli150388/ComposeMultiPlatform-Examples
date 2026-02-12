package com.example.geofencecmp

import androidx.compose.runtime.*

@Composable
fun App() {
    var currentScreen by remember { mutableStateOf("login") }

    when (currentScreen) {
        "login" -> LoginScreen(onLoginSuccess = { currentScreen = "attendance" }, onNavigateToSignup = { currentScreen = "signup" })
        "signup" -> SignupScreen(onSignupSuccess = { currentScreen = "login" }, onNavigateToLogin = { currentScreen = "login" })
        "attendance" -> AttendanceScreen()
    }
}