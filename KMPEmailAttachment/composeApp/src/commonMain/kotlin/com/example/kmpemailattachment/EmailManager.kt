package com.example.kmpemailattachment

expect class EmailManager {
    fun sendEmail(
        recipient: String,
        subject: String,
        body: String
    )
}

expect fun getEmailManager(): EmailManager