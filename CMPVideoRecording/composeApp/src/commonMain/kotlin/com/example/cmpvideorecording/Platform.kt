package com.example.cmpvideorecording

import androidx.compose.runtime.Composable

interface Platform {
    val name: String
}


@Composable
expect fun VideoRecordScreen(onVideoRecorded: (String) -> Unit, onBack: () -> Unit)

@Composable
expect fun VideoPlayerScreen(videoItem: VideoItem, onBack: () -> Unit)
