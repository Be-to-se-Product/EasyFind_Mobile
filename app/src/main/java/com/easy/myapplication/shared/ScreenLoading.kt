package com.easy.myapplication.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.easy.myapplication.shared.LoadingCircular.LoadingCircular

@Composable
fun ScreenLoading(isLoading: Boolean = false,text:String="Carregando suas informações...") {
    if (!isLoading) return
    Box(
        modifier = Modifier
            .background(Color.Black.copy(0.9f))
            .fillMaxWidth()
            .fillMaxHeight()
            .zIndex(5f)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(bottom = 10.dp),
                fontSize = 20.sp
            )
            LoadingCircular(width = 40.dp)


        }

    }
}