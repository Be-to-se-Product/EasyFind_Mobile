package com.easy.myapplication.screens.Pedido

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.easy.myapplication.shared.Header.Header

@RequiresApi(Build.VERSION_CODES.O)
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


