package com.jscj.qrcodescanner.qrcode

import androidx.camera.core.ImageProxy
import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.Result
import com.google.zxing.common.HybridBinarizer

// TODO: This at some point should become a class, but for a single function right now,
// we can leave it as a function so that it is easily accessible from other classes.

fun scanQRCode(imageProxy: ImageProxy, scanner: MultiFormatReader): Result? {
    val data = imageProxy.planes[0].buffer.let { buffer ->
        val data = ByteArray(buffer.capacity())
        buffer.get(data)
        buffer.clear()
        data
    }
    val source = PlanarYUVLuminanceSource(
        data,
        imageProxy.width,
        imageProxy.height,
        0,
        0,
        imageProxy.width,
        imageProxy.height,
        false
    )
    val binaryBitmap = BinaryBitmap(HybridBinarizer(source))
    return try {
        scanner.decodeWithState(binaryBitmap)
    } catch (e: Exception) {
        null // QR Code not found
    }
}