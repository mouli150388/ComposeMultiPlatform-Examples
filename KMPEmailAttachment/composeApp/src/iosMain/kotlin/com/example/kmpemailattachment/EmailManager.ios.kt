package com.example.kmpemailattachment

import platform.Foundation.NSURL
import platform.Foundation.NSURLComponents
import platform.Foundation.NSURLQueryItem
import platform.UIKit.UIApplication

actual class EmailManager {
    actual fun sendEmail(
        recipient: String,
        subject: String,
        body: String
    ) {
        val urlComponents = NSURLComponents().apply {
            setScheme("mailto")
            setPath(recipient)
            setQueryItems(listOf(
                NSURLQueryItem(name = "subject", value = subject),
                NSURLQueryItem(name = "body", value = body)
            ))
        }

        val mailtoUrl = urlComponents.URL
        if (mailtoUrl != null && UIApplication.sharedApplication.canOpenURL(mailtoUrl)) {
            UIApplication.sharedApplication.openURL(mailtoUrl, options = emptyMap<Any?, Any?>()) { success ->
                if (!success) {
                    openBrowserFallback(recipient, subject, body)
                }
            }
        } else {
            openBrowserFallback(recipient, subject, body)
        }
    }

    private fun openBrowserFallback(recipient: String, subject: String, body: String) {
        // Fallback for Simulator or devices without a mail app: Open a web-based mail link
        // Using Gmail as an example fallback
        val webMailUrl = "https://mail.google.com/mail/?view=cm&fs=1&to=$recipient&su=${subject.encode()}&body=${body.encode()}"
        val url = NSURL(string = webMailUrl)
        if (url != null) {
            UIApplication.sharedApplication.openURL(url, options = emptyMap<Any?, Any?>(), completionHandler = null)
        }
    }

    private fun String.encode(): String {
        return this.replace(" ", "%20")
            .replace("\n", "%0A")
            // Add more basic encoding if needed or use platform APIs
    }
}

actual fun getEmailManager(): EmailManager = EmailManager()