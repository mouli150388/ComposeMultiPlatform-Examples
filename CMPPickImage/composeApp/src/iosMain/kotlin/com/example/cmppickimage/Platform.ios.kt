package com.example.cmppickimage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerOriginalImage
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.UIKit.UIResponder
import platform.UIKit.UIViewController
import platform.darwin.NSObject
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.UIKit.UIImageJPEGRepresentation
import platform.posix.memcpy

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

private class ImagePickerDelegate(
    private val onImagePicked: (UIImage?) -> Unit
) : NSObject(), UIImagePickerControllerDelegateProtocol, UINavigationControllerDelegateProtocol {
    override fun imagePickerController(
        picker: UIImagePickerController,
        didFinishPickingMediaWithInfo: Map<Any?, *>
    ) {
        val image = didFinishPickingMediaWithInfo[UIImagePickerControllerOriginalImage] as? UIImage
        onImagePicked(image)
        picker.dismissViewControllerAnimated(true, null)
    }

    override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
        onImagePicked(null)
        picker.dismissViewControllerAnimated(true, null)
    }
}

private fun findViewController(): UIViewController? {
    val keyWindow = UIApplication.sharedApplication.keyWindow
    var responder: UIResponder? = keyWindow?.rootViewController
    while (responder != null) {
        if (responder is UIViewController) {
            return responder
        }
        responder = responder.nextResponder
    }
    return null
}

@OptIn(ExperimentalForeignApi::class)
private fun UIImage.toImageBitmap(): ImageBitmap? {
    val byteArray = this.toByteArray() ?: return null
    return Image.makeFromEncoded(byteArray).toComposeImageBitmap()
}

@OptIn(ExperimentalForeignApi::class)
private fun UIImage.toByteArray(): ByteArray? {
    val data: NSData = UIImageJPEGRepresentation(this, 0.9) ?: return null
    val bytes = data.bytes ?: return null
    val length = data.length.toInt()
    val byteArray = ByteArray(length)
    byteArray.usePinned { pinned ->
        memcpy(pinned.addressOf(0), bytes, data.length)
    }
    return byteArray
}

