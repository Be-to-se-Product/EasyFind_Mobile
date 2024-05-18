package com.easy.myapplication

import MapaViewModel
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.easy.myapplication.screens.Compra.Buy
import com.easy.myapplication.screens.Login.Login
import com.easy.myapplication.screens.Login.Model
import com.easy.myapplication.screens.Mapa.Mapa
import com.easy.myapplication.screens.Produto.Produto
import com.easy.myapplication.screens.Produto.ProdutoViewModel
import com.easy.myapplication.services.Service
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun     AppNavHost(
    modifier: Modifier = Modifier,
    startDestination: String,
) {
    val navController = LocalNavController.current

    Service.navigate = {
        navController.navigate("/Login")
    }


        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = startDestination
        ) {
            composable("Login")
            {
                val model = koinViewModel<Model>()
                Login( model, rememberNavController())
            }
            composable("Mapa") {
                val mapa = MapaViewModel()
                Mapa(mapa)
            }
            composable("Produto/{id}") { backStackEntry ->
                val view = ProdutoViewModel()
                Produto(view,backStackEntry.arguments?.getString("id"))
            }
            composable("RealizarPedido")
            {
                Buy()
            }
        }
    }
