package com.easy.myapplication.shared.CardCarrinho

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easy.myapplication.R
import com.easy.myapplication.screens.Produto.MainImageColumn
import com.easy.myapplication.shared.CarrinhoQuantidade.CarrinhoQuantidade
import com.easy.myapplication.shared.Subtitle.Subtitle
import java.io.Serializable

data class DataItemCarrinho(
    val id: Long? = 0,
    val name: String? = "",
    val business: String? = "",
    val preco: Double? = 0.0,
    val quantidade: Int? =0,
    val imagem: List<String>? = null
): Serializable
@Composable
fun CardCarrinho(data: DataItemCarrinho){
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                PhotoComponent(images = data.imagem!!)
            }
            Column(modifier = Modifier.weight(2f)) {
                Column {
                    Subtitle(content = data.name, color = Color.White, fontSize = 16.sp)
                }

                Column {
                    Subtitle(content = data.business, color = Color.White, fontSize = 14.sp)
                }

                Column {
                    Subtitle(content = "R$ "+data.preco, color = Color.White, fontSize = 10.sp)
                }
            }
        }
        CarrinhoQuantidade(data.id!!, data.quantidade!!)
    }
}

@Composable
fun PhotoComponent(images:List<String>) {
    if (images.isEmpty()){
        return Column(modifier = Modifier.size(width = 86.dp, height = 86.dp).clip(
            RoundedCornerShape(10.dp)
        )
        ) {
            Image(painter = painterResource(id = R.mipmap.default_produto), contentDescription = "tela de morango")
        }
    }
    val selectedImageState = remember { mutableStateOf(images[0]) }

    Column(modifier = Modifier.size(width = 86.dp, height = 86.dp)) {
        MainImageColumn(selectedImageState)
    }
}