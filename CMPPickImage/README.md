# Image Picker for Compose Multiplatform (Android & iOS)

This project demonstrates how to implement a native image picker in a Compose Multiplatform application using the `expect`/`actual` pattern. It allows users to select an image from the gallery on both Android and iOS devices.

## Implementation Overview

The solution uses a unified interface defined in the `commonMain` source set, with platform-specific implementations in `androidMain` and `iosMain`.

### 1. Common Implementation (`commonMain`)

Define the `ImagePickerLauncher` interface and the `expect` function in your shared code.

```kotlin
// In commonMain
interface ImagePickerLauncher {
    fun launch()
}

@Composable
expect fun rememberImagePickerLauncher(onResult: (ImageBitmap?) -> Unit): ImagePickerLauncher
```

### 2. Android Implementation (`androidMain`)

On Android, we use `ActivityResultContracts.PickVisualMedia` for a modern and secure way to access the gallery.

```kotlin
// In androidMain
@Composable
actual fun rememberImagePickerLauncher(onResult: (ImageBitmap?) -> Unit): ImagePickerLauncher {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                val inputStream = context.contentResolver.openInputStream(it)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                onResult(bitmap?.asImageBitmap())
            } ?: onResult(null)
        }
    )

    return remember {
        object : ImagePickerLauncher {
            override fun launch() {
                launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        }
    }
}
```

### 3. iOS Implementation (`iosMain`)

On iOS, we use `UIImagePickerController` and implement the necessary delegates to handle image selection and conversion.

```kotlin
// In iosMain
@Composable
actual fun rememberImagePickerLauncher(onResult: (ImageBitmap?) -> Unit): ImagePickerLauncher {
    val imagePickerController = remember { UIImagePickerController() }
    val imagePickerDelegate = remember {
        ImagePickerDelegate(
            onImagePicked = { image ->
                onResult(image?.toImageBitmap())
            }
        )
    }
    imagePickerController.delegate = imagePickerDelegate

    return remember {
        object : ImagePickerLauncher {
            override fun launch() {
                val viewController = findViewController()
                viewController?.presentViewController(imagePickerController, animated = true, completion = null)
            }
        }
    }
}

// Helper to convert UIImage to ImageBitmap using Skia
private fun UIImage.toImageBitmap(): ImageBitmap? {
    val byteArray = this.toByteArray() ?: return null
    return Image.makeFromEncoded(byteArray).toComposeImageBitmap()
}
```

### 4. Usage in UI

Using the image picker in your Compose UI is straightforward and platform-agnostic.

```kotlin
@Composable
fun App() {
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    
    val pickerLauncher = rememberImagePickerLauncher { bitmap ->
        imageBitmap = bitmap
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        imageBitmap?.let {
            Image(bitmap = it, contentDescription = "Selected Image")
        }
        Button(onClick = { pickerLauncher.launch() }) {
            Text("Pick Image")
        }
    }
}
```

## Key Features
- **Native Experience:** Uses the standard system gallery picker on both platforms.
- **Unified API:** A single Composable call works across Android and iOS.
- **Skia Integration:** Seamless conversion from native image formats to `ImageBitmap` for rendering in Compose.
