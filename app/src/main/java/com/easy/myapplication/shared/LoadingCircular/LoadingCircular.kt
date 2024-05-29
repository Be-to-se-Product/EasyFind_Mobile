package com.easy.myapplication.shared.LoadingCircular

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.easy.myapplication.ui.theme.Primary
import com.easy.myapplication.ui.theme.Seconday

@Composable
fun LoadingCircular(width:Dp=25.dp) {
    CircularProgressIndicator(
        modifier = Modifier.width(width).height(width),
        strokeWidth = 3.dp,
        color = Primary,
        trackColor = Seconday,
    )
}


