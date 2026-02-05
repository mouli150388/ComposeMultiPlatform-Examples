package com.example.cmppickimage

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

interface Platform {
    val name: String
}



/**
 * A helper to handle platform-specific image picking.
 */
@Composable
expect fun rememberImagePickerLauncher(onResult: (ImageBitmap?) -> Unit): ImagePickerLauncher

interface ImagePickerLauncher {
    fun launch()
}