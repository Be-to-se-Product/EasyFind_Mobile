package com.easy.myapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.easy.myapplication.ui.theme.Poppins


@Composable
fun Login(){
    
    Column {

        ButtonCustomize(onClick = { /*TODO*/ }) {
            Text("Login", fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        }

    }
    
}