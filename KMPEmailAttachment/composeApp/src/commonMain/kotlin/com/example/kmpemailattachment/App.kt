package com.example.kmpemailattachment

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun App() {
    MaterialTheme {
    var recipient by remember { mutableStateOf("example@email.com") }
    var subject by remember { mutableStateOf("Hello from KMP") }
    var body by remember { mutableStateOf("This is a test email sent from Compose Multiplatform!") }

    val emailManager = remember { getEmailManager() }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .safeContentPadding()
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Email Sender",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = recipient,
            onValueChange = { recipient = it },
            label = { Text("To") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = subject,
            onValueChange = { subject = it },
            label = { Text("Subject") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = body,
            onValueChange = { body = it },
            label = { Text("Body") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                emailManager.sendEmail(recipient, subject, body)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Send Email")
        }
    }
}
}