package com.easy.myapplication

import MapaViewModel
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.easy.myapplication.screens.Login.Login
import com.easy.myapplication.screens.Mapa.Mapa
import com.easy.myapplication.screens.Produto.Produto
import com.easy.myapplication.screens.Produto.ProdutoViewModel

@RequiresApi(Build.VERSION_CODES.O)
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
            Login(rememberNavController())
        }
        composable("Mapa"){
            val mapa = MapaViewModel()
            Mapa(mapa,navController)
        }
        composable("Produto"){
            val view = ProdutoViewModel()
            Produto(view,navController)
        }
    }
}