package com.easy.myapplication.screens.Pedido

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.easy.myapplication.LocalNavController
import com.easy.myapplication.dto.ResponseItemVendaDTO
import com.easy.myapplication.dto.ResponsePedido
import com.easy.myapplication.screens.Produto.ProdutoPedido
import com.easy.myapplication.shared.BarTitle.BarTitle
import com.easy.myapplication.shared.CardOrder.CardOrder
import com.easy.myapplication.shared.CardOrder.DataCardOrder
import com.easy.myapplication.shared.CardProduct.CardProduct
import com.easy.myapplication.shared.CardProduct.DataCardProduct
import com.easy.myapplication.shared.SelectBox.SelectBox
import com.easy.myapplication.shared.Subtitle.Subtitle

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

    Surface(color = Color(0xFF292929)) {
        Column {
            BarTitle(rota = "Mapa", title = "Meus Pedidos")
            SelectBox(value = statusState.value?:"",
                onValueChange = {},
                expandido = expandido,
                label = "",
                generos = status,
                genero = statusState
            )

            if(pedidos.isNotEmpty()){
                LazyColumn{
                    items(items = pedidos, itemContent = { pedido ->
                        var totalPreco = 0.0

                        pedido.itens?.forEach { itens ->
                            totalPreco += itens.produto?.preco ?: 0.0
                        }

                        CardOrder(data = DataCardOrder(
                            id = pedido.id?: 0L,
                            comercioName = pedido.estabelecimento?.nome.toString(),
                            status = pedido.statusPedido.toString(),
                            data = pedido.dataPedido.toString(),
                            metodoPagamento = pedido.metodoPagamento.toString(),
                            modoDeCompra = pedido.isPagamentoOnline.toString(),
                            preco = totalPreco,
                            produtos = pedido
                            )
                        )
                    })

                }
            }
        }
    }
}
@Composable
fun ItensPedido(){
    val navController = LocalNavController.current;
    val products = navController.previousBackStackEntry?.savedStateHandle?.get<ResponsePedido>("PEDIDOS")?.itens as List<ResponseItemVendaDTO>
    Surface(color = Color(0xFF292929)) {
        Column {
            BarTitle(rota = "Pedidos", title = "Itens do pedido")
            LazyColumn{
                items(items = products , itemContent = { product->

                    CardProduct(data = DataCardProduct(
                        name = product.produto?.nome,
                        preco = product.produto?.preco,
                        quantidade = product.quantidade
                        ))

                })
            }
        }
    }
}
