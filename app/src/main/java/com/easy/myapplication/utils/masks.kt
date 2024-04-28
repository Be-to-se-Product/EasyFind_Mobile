package com.easy.myapplication.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import kotlin.math.max
import kotlin.math.min

fun CpfMaskTransformation() = VisualTransformation { text ->
    val trimmed = if (text.text.length >= 11) text.text.substring(0, 11) else text.text
    val transformed = StringBuilder()
    val offsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int) = offset + when (offset) {
            in 0..2 -> 0
            in 3..5 -> 1
            in 6..8 -> 2
            else -> 3
        }
        override fun transformedToOriginal(offset: Int) = offset - when (offset) {
            in 0..3 -> 0
            in 4..7 -> 1
            in 8..11 -> 2
            else -> 3
        }
    }
    trimmed.forEachIndexed { index, char ->
        transformed.append(char)
        if (index == 2 || index == 5) transformed.append('.')
        if (index == 8) transformed.append('-')
    }
    TransformedText(AnnotatedString(transformed.toString()), offsetTranslator)
}
class DateMaskTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        var out = ""
        when (text.text.length) {
            in 1..2 -> out = text.text
            in 3..4 -> out = text.text.substring(0, 2) + "/" + text.text.substring(2)
            in 5..8 -> out = text.text.substring(0, 2) + "/" + text.text.substring(2, 4) + "/" + text.text.substring(4)
            else -> out = text.text
        }
        val outputText = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.White)) {
                append(out)
            }
        }
        val offsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when (offset) {
                    in 0..2 -> offset
                    in 3..4 -> offset + 1
                    in 5..8 -> offset + 2
                    else -> offset
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when (offset) {
                    in 0..2 -> offset
                    in 4..5 -> offset - 1
                    in 7..10 -> offset - 2
                    else -> offset
                }
            }
        }
        return TransformedText(outputText, offsetMapping = offsetTranslator)
    }
}
@Composable
fun PhoneMaskTransformation() = VisualTransformation { text ->
    val trimmed = if (text.text.length >= 11) text.text.substring(0..10) else text.text
    val transformed = when (trimmed.length) {
        in 0..1 -> trimmed
        in 2..6 -> "(${trimmed.substring(0, 2)}) ${trimmed.substring(2)}"
        in 7..10 -> "(${trimmed.substring(0, 2)}) ${trimmed.substring(2, 3)} ${trimmed.substring(3, 7)}-${trimmed.substring(7)}"
        else -> "(${trimmed.substring(0, 2)}) ${trimmed.substring(2, 6)}-${trimmed.substring(6)}"
    }
    TransformedText(AnnotatedString(transformed), object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return when (offset) {
                in 0..1 -> offset
                in 2..5 -> offset + 3
                in 6..9 -> offset + 4
                else -> min(offset + 5, transformed.length)
            }
        }

        override fun transformedToOriginal(offset: Int): Int {
            return when (offset) {
                in 0..1 -> offset
                in 4..7 -> offset - 3
                in 9..12 -> offset - 4
                else -> max(offset - 5, 0)
            }
        }
    })
}