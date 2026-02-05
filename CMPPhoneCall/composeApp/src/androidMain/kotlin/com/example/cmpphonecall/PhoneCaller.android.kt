package com.example.cmpphonecall

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

class AndroidPhoneCaller(private val context: Context) : PhoneCaller {
    override fun makeCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }
}

@Composable
actual fun rememberPhoneCaller(): PhoneCaller {
    val context = LocalContext.current
    return remember(context) { AndroidPhoneCaller(context) }
}