package com.easy.myapplication.screens.Carrinho

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.easy.myapplication.LocalNavController
import com.easy.myapplication.screens.Produto.ProdutoPedido
import com.easy.myapplication.shared.BarTitle.BarTitle
import com.easy.myapplication.shared.Button.Button
import com.easy.myapplication.shared.CardCarrinho.CardCarrinho
import com.easy.myapplication.shared.CardCarrinho.DataItemCarrinho
import com.easy.myapplication.shared.Header.Header

@Composable
fun Carrinho(viewModel:Model){
    val model = viewModel.getCarrinhos()
    val carrinhos = viewModel.carrinhos.observeAsState().value!!
    val groupedCarrinhos = carrinhos.groupBy { it.nomeEmpresa }
    val navController = LocalNavController.current;

    Header {
        Surface(color = Color(0xFF292929), modifier = Modifier.fillMaxSize()) {
            Column {
                BarTitle(rota = "Mapa", title = "Carrinho")
                if(carrinhos.isNotEmpty()) {
                    LazyColumn {
                        groupedCarrinhos.forEach { (nomeEmpresa, carrinhos) ->
                            val produtoVenda = carrinhos.map { carrinho ->
                                ProdutoPedido(
                                    id = carrinho.produto?.id,
                                    quantidade = carrinho.quantidade,
                                    idEstabelecimento = carrinho.idEmpresa,
                                    preco = carrinho.produto?.preco
                                )
                            }
                            items(items = carrinhos, itemContent = { carrinho ->
                                CardCarrinho(data = DataItemCarrinho(
                                    id = carrinho.id,
                                    name = carrinho.produto?.nome,
                                    business = carrinho.nomeEmpresa,
                                    preco = carrinho.produto?.preco,
                                    quantidade = carrinho.quantidade,
                                    imagem = carrinho.produto?.imagens
                                ))
                            })
                            item {
                                Spacer(modifier = Modifier.padding(top = 40.dp))
                                Column(modifier = Modifier.padding(end = 16.dp, start = 16.dp)) {
                                    Button(
                                        onClick = {
                                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                                "PRODUTO",
                                                produtoVenda
                                            )
                                            navController.navigate("RealizarPedido")
                                        }, content = { Text(text = "Finalizar pedido") }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
