package com.example.cmpphonecall

import androidx.compose.runtime.Composable

@Composable
expect fun rememberPhoneCaller(): PhoneCaller

interface PhoneCaller {
    fun makeCall(phoneNumber: String)
}