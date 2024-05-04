package com.easy.myapplication.utils

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel

fun generateQRCode(content: String): Bitmap {
    val qrCodeWriter = QRCodeWriter()
    val hints = mapOf(
        EncodeHintType.ERROR_CORRECTION to ErrorCorrectionLevel.L
    )
    val bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 300, 300, hints)

    val bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.RGB_565)
    for (x in 0 until 300) {
        for (y in 0 until 300) {
            bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.Black.toArgb() else Color.White.toArgb())
        }
    }
    return bitmap
}