package com.easy.myapplication.shared.ButtonQuantidadeProduto

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easy.myapplication.R

@Composable
fun ProdutoQuantityButton(
    quantity: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)
            .border(
                width = 1.dp,
                color = Color(0xFFFCA622),
                shape = RoundedCornerShape(8.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        IconButton(
            onClick = {
                if (quantity > 0){
                    onDecrement()
                }
            }
        ) {
            Icon(
                painter = painterResource(id = R.mipmap.remover)
                ,contentDescription = "Remover a quantidade"
                ,tint = Color(0xFFFCA622)
            )
        }
        Text(
            text = quantity.toString(),
            style = TextStyle(fontSize = 18.sp),
            fontWeight = FontWeight.Bold
        )

        IconButton(
            onClick = {
                onIncrement()
            }
        ) {
            Icon(
                painter = painterResource(id = R.mipmap.adicionar)
                ,contentDescription = "Adicionar quantidade"
                , tint = Color(0xFFFCA622)
            )
        }
    }
}