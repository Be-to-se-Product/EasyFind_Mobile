package com.easy.myapplication.shared.CardProduct

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easy.myapplication.dto.ResponsePedido
import com.easy.myapplication.shared.Subtitle.Subtitle
import java.io.Serializable

data class DataCardProduct(
    val name: String? = "",
    val preco: Double? = 0.0,
    val quantidade: Int? =0,
    val imagem: List<String>
): Serializable
@Composable
fun CardProduct(data: DataCardProduct){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {

        }

        Column(modifier = Modifier.weight(2f) ){

            Column {
                Subtitle(content = data.name, color = Color.White, fontSize = 16.sp)
            }

            Column {
                Subtitle(content = "R$ "+data.preco, color = Color.White, fontSize = 14.sp)
            }

            Column {
                Subtitle(content = "Quantidade: "+data.quantidade, color = Color.White, fontSize = 10.sp)
            }
        }
    }
}