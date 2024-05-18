package com.easy.myapplication.shared.Button

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.easy.myapplication.shared.LoadingCircular.LoadingCircular

@Composable
fun Button(
    onClick: () -> Unit, content: @Composable RowScope.() -> Unit,isLoading:Boolean=false
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(20), modifier = Modifier.fillMaxWidth(fraction = 1f),
        colors = ButtonDefaults.buttonColors(Color(0xFFFCA622))
    ){

        if(isLoading){
            LoadingCircular()
        }
        else{
            content()
        }
    }
}