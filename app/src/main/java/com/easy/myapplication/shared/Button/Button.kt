package com.easy.myapplication.shared.Button

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ButtonCustomize(
    onClick: () -> Unit, content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick, content = content,
        shape = RoundedCornerShape(20), modifier = Modifier.fillMaxWidth(fraction = 1f),

        colors = ButtonDefaults.buttonColors(Color(0xFFFCA622))
    )
}