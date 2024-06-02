package com.easy.myapplication.shared.CardOrder

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easy.myapplication.LocalNavController
import com.easy.myapplication.dto.ResponsePedido
import com.easy.myapplication.shared.Subtitle.Subtitle
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class DataCardOrder(
    val id:Long=0L,
    val comercioName: String? = "",
    val status: String? = "",
    val data: String? = "",
    val metodoPagamento: String? = "",
    val modoDeCompra: String? = "",
    val preco: Double? = 0.0,
    val produtos: ResponsePedido?
):Serializable

@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CardOrder(data : DataCardOrder){
    val shape = RoundedCornerShape(8.dp)
    val navController = LocalNavController.current;

    val formatoEntrada = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val formatoSaida = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm")

    val dataHora = LocalDateTime.parse(data.data, formatoEntrada)
    val dataFormatada = dataHora.format(formatoSaida)

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp).background(Color.White, shape)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 16.dp)
        ) {
            Row {
                Subtitle(content = data.comercioName, color = Color.Black)
            }
            Row {
                Subtitle(content = data.status, color = Color.Black)
            }
        }
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp)
        ) {
            Row {
                Subtitle(content = "Data do pedido", color = Color.Black)
            }
            Row {
                Subtitle(content = dataFormatada, color = Color.DarkGray)
            }
        }
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp)
        ) {
            Row {
                Subtitle(content = "Método de pagamento", color = Color.Black)
            }
            Row {
                Subtitle(content = data.metodoPagamento, color = Color.DarkGray)
            }
        }
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp)
        ) {
            Row {
                Subtitle(content = "Modo de compra", color = Color.Black)
            }
            Row {
                Subtitle(content = data.modoDeCompra, color = Color.DarkGray)
            }
        }
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Row {
                Subtitle(content = "Preço total: " + data.preco, color = Color.Black)
            }
            Button(
                onClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        "PEDIDOS",
                        data.produtos
                    )
                    navController.navigate("Itens")
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(Color.Black),
                    modifier = Modifier.wrapContentWidth()
            ) {
                Text(
                    text = "Itens do pedido",
                    fontSize = 12.sp,
                    maxLines = 1,
                    color = Color.White
                    )
            }
        }
    }
}
