package com.easy.myapplication.screens.Mapa

import DestinationTarget
import MapaViewModel
import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.easy.myapplication.BuildConfig
import com.easy.myapplication.R
import com.easy.myapplication.dto.Produto
import com.easy.myapplication.dto.RoutesMapper
import com.easy.myapplication.screens.Produto.ProdutoPedido
import com.easy.myapplication.shared.BarButton.BarButton
import com.easy.myapplication.shared.Header.Header
import com.easy.myapplication.shared.Input.Input
import com.easy.myapplication.shared.ProductItem.DataProductItem
import com.easy.myapplication.shared.ProductItem.ProductItem
import com.easy.myapplication.shared.ProductItem.Time
import com.easy.myapplication.shared.Subtitle.Subtitle
import com.easy.myapplication.shared.Title.Title
import com.easy.myapplication.ui.theme.Primary
import com.easy.myapplication.utils.LocationCallback
import com.easy.myapplication.utils.getLatLong
import com.easy.myapplication.utils.mediaAvaliacao


@Composable
fun Mapa(viewModel: MapaViewModel,navController: NavController) {

    val searchProduct = remember { mutableStateOf("") }
    val produtos = viewModel.produtos.observeAsState().value!!;
    val destination = viewModel.destination.observeAsState().value!!
    val routes = viewModel.routes.observeAsState().value!!;
    val context = LocalContext.current
    val (latLong, setLatLong) = remember { mutableStateOf(LatandLong()) }
    val locationCallback = object : LocationCallback {
        override fun onSuccess(latitude: Double, longitude: Double) {
            setLatLong(latLong.copy(latitude, longitude))
        }

        override fun onError(message: String?) {
            print(message)
        }
    }

    val getRouteCallback = object : GetRouteCallback {
        override fun getRoute(destination: DestinationTarget) {
            viewModel.getRoute(destination, latLong)
        }
    }

    LaunchedEffect(key1 = Unit) {
        getLatLong(context, locationCallback)
    }
    LaunchedEffect(key1 = latLong.latitude) {
        if (latLong.latitude != 0.0) {
            viewModel.getProdutos(latLong.latitude, latLong.longitude)

        }
    }



    BarButton(sheetContent = {

        if (routes.size <= 0) {
            BarProducts(produtos = produtos, getRouteCallback = getRouteCallback)
        } else {
            BarDirections(rotas = routes, destinationTarget = destination)
        }

    }) {

        Header {
            Row(
                horizontalArrangement = Arrangement.Center, modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(0.dp, 10.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Input(
                        value = searchProduct.value,
                        onValueChange = { searchProduct.value = it },
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )
                }
            }
            Text(text = navController.previousBackStackEntry?.savedStateHandle?.get<ProdutoPedido>("PRODUTO")?.preco.toString())
//            MyContent(originCoordinates = latLong, destinationCoordinates = destination.coordinates)


        }
    }
}


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun MyContent(originCoordinates: LatandLong, destinationCoordinates: LatandLong?) {
    var destinationString = ""
    if (destinationCoordinates != null) {
        destinationString =
            "&latitudeDestination=${destinationCoordinates.latitude}&longitudeDestination=${destinationCoordinates.longitude}"
    }
    val baseUrl = BuildConfig.HOST_WEB
    var mUrl =
        "${baseUrl}/mapa/mobile?latitudeOrigin=${originCoordinates.latitude}&longitudeOrigin=${originCoordinates.longitude}"

    mUrl += destinationString;

    Column(modifier = Modifier.height(9000.dp)) {
        AndroidView(factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                loadUrl(mUrl)
            }
        }, update = {
            it.loadUrl(mUrl)
        })
    }
}


@Composable
fun Left() {
    Column() {
        Image(
            painter = painterResource(id = R.mipmap.left),
            contentDescription = "Marcador Inicio",
            modifier = Modifier.size(30.dp)
        )
    }

}


@Composable
fun Right() {
    Column {
        Image(
            painter = painterResource(id = R.mipmap.right),
            contentDescription = "Marcador Inicio",
            modifier = Modifier.size(30.dp)
        )
    }

}


@Composable
fun Siga() {
    Column {
        Image(
            painter = painterResource(id = R.mipmap.up),
            contentDescription = "Marcador Inicio",
            modifier = Modifier.size(40.dp)
        )
    }

}

@Composable
fun Back() {
    Column {
        Image(
            painter = painterResource(id = R.mipmap.bottom),
            contentDescription = "Marcador Inicio",
            modifier = Modifier.size(40.dp)
        )
    }
}


@Composable
fun BarDirections(rotas: List<RoutesMapper>,destinationTarget: DestinationTarget) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                destinationTarget.estabelecimento?.nome?.let { Title(content = it) }
            }
            Row {
                Subtitle(content = "4min(300m)", color = Primary)
            }
        }


        LazyColumn(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            items(items = rotas, itemContent = {
                Row {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(0.9f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(30.dp)
                        ) {
                             when (it.modifier) {
                                "left" -> Left()
                                "right" -> Right()
                                else -> Siga()

                            }
                            Column(verticalArrangement = Arrangement.Center) {
                                Subtitle(
                                    content = it.instruction,
                                    color = Primary
                                )
                            }
                        }
                    }
                }
            })
        }
    }

}

@Composable
fun BarProducts(produtos: List<Produto>, getRouteCallback: GetRouteCallback) {
    Column {
        LazyColumn {
            items(items = produtos, itemContent = {
                ProductItem(
                    getRouteCallback = getRouteCallback,
                    data = DataProductItem(
                        name = it.nome.toString(),
                        qtdStars = mediaAvaliacao(it.avaliacao),
                        shop = it.estabelecimento?.nome ?: "",
                        price = it.precoAtual ?: 0.0,
                        time = Time(
                            it.estabelecimento?.tempoCarro,
                            it.estabelecimento?.tempoCarro,
                            it.estabelecimento?.tempoCarro
                        ),
                        latitude = it.estabelecimento?.endereco?.latitude,
                        longitude = it.estabelecimento?.endereco?.longitude,
                        estabelecimento = it.estabelecimento
                    )
                )
            })
        }


    }
}


data class LatandLong(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)