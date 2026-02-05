package com.example.cmpvideorecording

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun App() {
    MaterialTheme {
        var screenState by remember { mutableStateOf<Screen>(Screen.List) }
        val videoList = remember { mutableStateListOf<VideoItem>() }
        var selectedVideo by remember { mutableStateOf<VideoItem?>(null) }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            when (val state = screenState) {
                is Screen.List -> {
                    VideoListScreen(
                        videoList = videoList,
                        onRecordClick = { screenState = Screen.Record },
                        onVideoClick = { video ->
                            selectedVideo = video
                            screenState = Screen.Play
                        }
                    )
                }
                is Screen.Record -> {
                    VideoRecordScreen(
                        onVideoRecorded = { path ->
                            videoList.add(VideoItem("Video ${videoList.size + 1}", path))
                            screenState = Screen.List
                        },
                        onBack = { screenState = Screen.List }
                    )
                }
                is Screen.Play -> {
                    selectedVideo?.let { video ->
                        VideoPlayerScreen(
                            videoItem = video,
                            onBack = { screenState = Screen.List }
                        )
                    }
                }
            }
        }
    }
}

sealed class Screen {
    object List : Screen()
    object Record : Screen()
    object Play : Screen()
}

@Composable
fun VideoListScreen(
    videoList: List<VideoItem>,
    onRecordClick: () -> Unit,
    onVideoClick: (VideoItem) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onRecordClick) {
                Text("+", style = MaterialTheme.typography.headlineMedium)
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            Text(
                "Recorded Videos",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )
            LazyColumn {
                items(videoList) { video ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable { onVideoClick(video) }
                    ) {
                        Text(
                            video.name,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}
