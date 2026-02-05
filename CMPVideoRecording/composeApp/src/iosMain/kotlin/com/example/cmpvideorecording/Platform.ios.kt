package com.example.cmpvideorecording

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.unit.dp
import kotlinx.cinterop.*
import platform.AVFoundation.*
import platform.AVKit.*
import platform.CoreGraphics.*
import platform.CoreMedia.*
import platform.Foundation.*
import platform.QuartzCore.*
import platform.UIKit.*
import platform.darwin.NSObject

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

@OptIn(ExperimentalForeignApi::class)
class VideoRecorderDelegate(private val onVideoRecorded: (String) -> Unit) : NSObject(), AVCaptureFileOutputRecordingDelegateProtocol {
    override fun captureOutput(
        output: AVCaptureFileOutput,
        didFinishRecordingToOutputFileAtURL: NSURL,
        fromConnections: List<*>,
        error: NSError?
    ) {
        if (error == null) {
            onVideoRecorded(didFinishRecordingToOutputFileAtURL.absoluteString ?: "")
        }
    }
}

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun VideoRecordScreen(onVideoRecorded: (String) -> Unit, onBack: () -> Unit) {
    val captureSession = remember { AVCaptureSession() }
    val movieOutput = remember { AVCaptureMovieFileOutput() }
    val previewLayer = remember { AVCaptureVideoPreviewLayer(session = captureSession) }
    val delegate = remember { VideoRecorderDelegate(onVideoRecorded) }
    var isRecording by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val device = AVCaptureDevice.defaultDeviceWithMediaType(AVMediaTypeVideo)
        if (device != null) {
            val input = AVCaptureDeviceInput.deviceInputWithDevice(device, null) as? AVCaptureDeviceInput
            if (input != null && captureSession.canAddInput(input)) {
                captureSession.addInput(input)
            }
        }
        val audioDevice = AVCaptureDevice.defaultDeviceWithMediaType(AVMediaTypeAudio)
        if (audioDevice != null) {
            val audioInput = AVCaptureDeviceInput.deviceInputWithDevice(audioDevice, null) as? AVCaptureDeviceInput
            if (audioInput != null && captureSession.canAddInput(audioInput)) {
                captureSession.addInput(audioInput)
            }
        }
        if (captureSession.canAddOutput(movieOutput)) {
            captureSession.addOutput(movieOutput)
        }
        captureSession.startRunning()
    }

    DisposableEffect(Unit) {
        onDispose {
            captureSession.stopRunning()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        UIKitView(
            factory = {
                val container = UIView()
                previewLayer.frame = container.bounds
                previewLayer.videoGravity = AVLayerVideoGravityResizeAspectFill
                container.layer.addSublayer(previewLayer)
                container
            },
            modifier = Modifier.fillMaxSize(),
            update = { view ->
                CATransaction.begin()
                CATransaction.setValue(true, kCATransactionDisableActions)
                previewLayer.frame = view.bounds
                CATransaction.commit()
            }
        )

        Column(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    if (!isRecording) {
                        val outputPath = NSTemporaryDirectory() + NSUUID().UUIDString() + ".mp4"
                        val outputURL = NSURL.fileURLWithPath(outputPath)
                        movieOutput.startRecordingToOutputFileURL(outputURL, delegate)
                        isRecording = true
                    } else {
                        movieOutput.stopRecording()
                        isRecording = false
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!isRecording) Color.Red else Color.Gray
                )
            ) {
                Text(if (!isRecording) "Start Recording" else "Stop Recording")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onBack) {
                Text("Back")
            }
        }
    }
}

@OptIn(ExperimentalForeignApi::class, ExperimentalMaterial3Api::class)
@Composable
actual fun VideoPlayerScreen(videoItem: VideoItem, onBack: () -> Unit) {
    val player = remember {
        val url = NSURL.URLWithString(videoItem.path)
        if (url != null) AVPlayer(uRL = url) else AVPlayer()
    }

    DisposableEffect(Unit) {
        player.play()
        onDispose {
            player.pause()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(videoItem.name) },
                navigationIcon = {
                    Button(onClick = onBack) { Text("Back") }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            UIKitView(
                factory = {
                    val playerLayer = AVPlayerLayer.playerLayerWithPlayer(player)
                    val container = UIView()
                    playerLayer.frame = container.bounds
                    playerLayer.videoGravity = AVLayerVideoGravityResizeAspect
                    container.layer.addSublayer(playerLayer)
                    container
                },
                modifier = Modifier.fillMaxSize(),
                update = { view ->
                    view.layer.sublayers?.firstOrNull()?.let {
                        if (it is AVPlayerLayer) {
                            it.frame = view.bounds
                        }
                    }
                }
            )
        }
    }
}