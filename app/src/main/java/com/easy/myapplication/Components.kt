package com.easy.myapplication

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun ButtonCustomize(onClick : ()->Unit,  content: @Composable RowScope.() -> Unit
){
    Button(
        onClick=onClick,content=content,
        shape = RoundedCornerShape(20), modifier = Modifier.fillMaxWidth(fraction = 1f))
}