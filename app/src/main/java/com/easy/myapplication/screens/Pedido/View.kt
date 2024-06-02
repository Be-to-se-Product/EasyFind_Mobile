package com.easy.myapplication.screens.Pedido

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.easy.myapplication.shared.Header.Header

@Composable
fun Pedidos(nav: NavHostController, viewModel: Model){

    Header {
        NavHost(
            navController = nav,
            startDestination = "PEDIDOS"
        ) {
            composable("PEDIDOS") {
                Pedidos(viewModel)
            }
            composable("ITENS") {
                ItensPedido()
            }
        }

    }
}


