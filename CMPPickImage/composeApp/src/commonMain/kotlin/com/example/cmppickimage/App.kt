package com.example.cmppickimage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun App() {
    MaterialTheme {
        var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
        
        // This launcher uses expect/actual to handle platform-specific image picking
        val pickerLauncher = rememberImagePickerLauncher { bitmap ->
            imageBitmap = bitmap
        }

        AppContent(
            imageBitmap = imageBitmap,
            onPickImageClick = { pickerLauncher.launch() }
        )
    }
}

@Composable
fun AppContent(
    imageBitmap: ImageBitmap?,
    onPickImageClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeContentPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (imageBitmap != null) {
            Image(
                bitmap = imageBitmap,
                contentDescription = "Selected Image",
                modifier = Modifier
                    .size(300.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text("No image selected", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onPickImageClick) {
            Text("Pick Image from Gallery")
        }
    }
}


@Preview
@Composable
fun AppPreview() {
    val pickerLauncher = rememberImagePickerLauncher { bitmap ->
       // imageBitmap = bitmap
    }
    MaterialTheme {
        AppContent(
            imageBitmap = null,
            onPickImageClick = {
                pickerLauncher.launch()
            }
        )
    }
}
