package com.example.cmpphonecall

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

class IosPhoneCaller : PhoneCaller {
    override fun makeCall(phoneNumber: String) {
        val url = NSURL(string = "tel:$phoneNumber")
        if (UIApplication.sharedApplication.canOpenURL(url)) {
            UIApplication.sharedApplication.openURL(url)
        }
    }
}

@Composable
actual fun rememberPhoneCaller(): PhoneCaller {
    return remember { IosPhoneCaller() }
}