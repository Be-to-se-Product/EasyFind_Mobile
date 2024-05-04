package com.easy.myapplication.shared.Subtitle

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun Subtitle(
    content: String?,
    fontSize: TextUnit = 12.sp,
    color: Color = Color.White
) {

    Text(text = content?:"", color = color, fontSize = fontSize, fontWeight = FontWeight.Normal)

}