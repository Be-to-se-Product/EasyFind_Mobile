package com.easy.myapplication.shared.CarrinhoQuantidade

import androidx.compose.foundation.background
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
import androidx.lifecycle.ViewModel
import com.easy.myapplication.R
import com.easy.myapplication.screens.Carrinho.Model

@Composable
fun CarrinhoQuantidade(id: Long, quantidade: Int){
    val model = Model();
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp, start = 16.dp)
            .border(
                width = 2.dp,
                color = Color(0xFF3E3E3E),
                shape = RoundedCornerShape(8.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        IconButton(
            onClick = {
                model.editCarrinho(id, quantidade - 1)
            }
        ) {
            Icon(
                painter = painterResource(id = R.mipmap.remover)
                ,contentDescription = "Remover a quantidade"
                ,tint = Color(0xFFFCA622)
            )
        }
        Text(
            text = ""+quantidade,
            style = TextStyle(fontSize = 18.sp),
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        IconButton(
            onClick = {
                model.editCarrinho(id, quantidade + 1)
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