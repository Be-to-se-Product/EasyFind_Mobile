package com.easy.myapplication

import MapaViewModel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.easy.myapplication.screens.Compra.Buy
import com.easy.myapplication.screens.Login.Login
import com.easy.myapplication.screens.Mapa.Mapa


@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String
){
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ){
        composable("Login")
        {
            Login()
        }
                composable("Mapa"){
                    val mapa = MapaViewModel()
                    Mapa(mapa)
        }

        composable("RealizarPedido")
        {
            Buy()
        }
    }
}