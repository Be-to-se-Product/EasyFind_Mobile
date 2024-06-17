package com.easy.myapplication.screens.Pedido

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.easy.myapplication.LocalNavController
import com.easy.myapplication.R
import com.easy.myapplication.dto.ResponseItemVendaDTO
import com.easy.myapplication.dto.ResponsePedido
import com.easy.myapplication.shared.BarTitle.BarTitle
import com.easy.myapplication.shared.CardOrder.CardOrder
import com.easy.myapplication.shared.CardOrder.DataCardOrder
import com.easy.myapplication.shared.CardProduct.CardProduct
import com.easy.myapplication.shared.CardProduct.DataCardProduct
import com.easy.myapplication.shared.Header.Header
import com.easy.myapplication.shared.SelectBox.SelectBox
import com.easy.myapplication.shared.Subtitle.Subtitle

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Pedidos(viewModel: Model){
    val status = remember {
        mutableStateListOf(
            "AGUARDANDO_RETIRADA",
            "ENTREGUE",
            "PENDENTE",
            "PREPARO",
            "CANCELADO"
        )
    }

    val statusState = remember { mutableStateOf("") }
    val expandido = remember { mutableStateOf(false) }
    val model = viewModel.getPedidos(statusState.value)
    val pedidos = viewModel.pedidos.observeAsState().value!!

    Surface(color = Color(0xFF292929), modifier = Modifier.fillMaxSize()) {
        Column {
            BarTitle(rota = "Mapa", title = stringResource(id = R.string.description_MeusPedidos))
            Column(
                modifier = Modifier
                    .width(220.dp)
                    .align(Alignment.End)
                    .padding(16.dp)
            ) {
                SelectBox(value = statusState.value?:"",
                    onValueChange = {},
                    expandido = expandido,
                    label = "",
                    generos = status,
                    genero = statusState
                )
            }

            if(pedidos.isNotEmpty()){
                LazyColumn{
                    items(items = pedidos, itemContent = { pedido ->
                        var totalPreco = 0.0
                        var modo = stringResource(id = R.string.description_ModoPagamentoOnline)

                        pedido.itens?.forEach { itens ->
                            var subTotal = itens.produto?.preco?.times(itens.quantidade!!)
                            if (subTotal != null) {
                                totalPreco += subTotal
                            }
                        }

                        if(pedido.isPagamentoOnline == false){
                            modo = stringResource(id = R.string.description_ModoPagamentoLoja)
                        }

                        CardOrder(data = DataCardOrder(
                            id = pedido.id?: 0L,
                            comercioName = pedido.estabelecimento?.nome.toString(),
                            status = pedido.statusPedido.toString(),
                            data = pedido.dataPedido.toString(),
                            metodoPagamento = pedido.metodoPagamento.toString(),
                            modoDeCompra = modo,
                            preco = totalPreco,
                            produtos = pedido
                            )
                        )
                    })

                }
            }else{
                Row(horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                ) {
                    Subtitle(content = stringResource(id = R.string.description_SemPedidos), color = Color.White)
                }
            }
        }
    }
}
@Composable
fun ItensPedido(){
    val navController = LocalNavController.current;
    val products = navController.previousBackStackEntry?.savedStateHandle?.get<ResponsePedido>("PEDIDOS")?.itens as List<ResponseItemVendaDTO>
    Header {
        Surface(color = Color(0xFF292929), modifier = Modifier.fillMaxSize()) {
            Column {
                BarTitle(rota = "Pedidos", title = stringResource(id = R.string.description_ItensPedidos))
                LazyColumn {
                    items(items = products, itemContent = { product ->

                        CardProduct(
                            data = DataCardProduct(
                                name = product.produto?.nome,
                                preco = product.produto?.preco,
                                quantidade = product.quantidade,
                                imagem = product.produto?.imagens ?: null
                            )
                        )

                    })
                }
            }
        }
    }
}
