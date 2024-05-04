package com.easy.myapplication.shared.Title

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun Title(
    content: String,
    fontSize: TextUnit = 20.sp,
    color: Color = Color.White,
    overflow: TextOverflow = TextOverflow.Visible,
    maxLines: Int= Int.MAX_VALUE,

    ) {
    Text(text = content, color = color, fontSize = fontSize, fontWeight = FontWeight.SemiBold,overflow = overflow,maxLines=maxLines)
}
