package com.example.kmpemailattachment

import android.content.Context
import android.content.Intent
import android.net.Uri

actual class EmailManager(private val context: Context) {
    actual fun sendEmail(
        recipient: String,
        subject: String,
        body: String
    ) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            val chooser = Intent.createChooser(intent, "Send Email")
            chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(chooser)
        }
    }
}

private lateinit var appContext: Context

fun initEmailManager(context: Context) {
    appContext = context.applicationContext
}

actual fun getEmailManager(): EmailManager = EmailManager(appContext)