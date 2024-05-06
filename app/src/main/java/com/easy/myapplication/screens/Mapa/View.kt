package com.easy.myapplication.screens.Mapa

import DestinationTarget
import LatandLong
import MapaViewModel
import android.os.Build
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.easy.myapplication.BuildConfig
import com.easy.myapplication.R
import com.easy.myapplication.dto.FilterDTO
import com.easy.myapplication.dto.Metodo
import com.easy.myapplication.dto.Produto
import com.easy.myapplication.dto.RoutesMapper
import com.easy.myapplication.shared.BarButton.BarButton
import com.easy.myapplication.shared.Header.Header
import com.easy.myapplication.shared.Input.Input
import com.easy.myapplication.shared.ModalBottomSheet.ModalBottomSheet
import com.easy.myapplication.shared.ProductItem.DataProductItem
import com.easy.myapplication.shared.ProductItem.ProductItem
import com.easy.myapplication.shared.ProductItem.Time
import com.easy.myapplication.shared.Subtitle.Subtitle
import com.easy.myapplication.shared.Title.Title
import com.easy.myapplication.ui.theme.Primary
import com.easy.myapplication.utils.LocationCallback
import com.easy.myapplication.utils.getLatLong
import com.easy.myapplication.utils.mediaAvaliacao


data class MetodoPagamentoDefault(
    val nome: String,
    val key: Metodo
)


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Mapa(viewModel: MapaViewModel) {
    val filter = viewModel.filterDTO.observeAsState().value!!;
    val setFilter = { it: FilterDTO -> viewModel.filterDTO.postValue(it) };
    val openFilter = remember { mutableStateOf(false) }
    val produtos = viewModel.produtos.observeAsState().value!!;
    val destination = viewModel.destination.observeAsState().value!!
    val routes = viewModel.routes.observeAsState().value!!;

    val context = LocalContext.current
    val latLong = viewModel.latLong.observeAsState().value!!;
    val setLatLong = { item: LatandLong ->
        viewModel.latLong.postValue(item)
    }


    val locationCallback = object : LocationCallback {
        override fun onSuccess(latitude: Double, longitude: Double) {
            setLatLong(latLong.copy(latitude, longitude))
        }

        override fun onError(message: String?) {
            print(message)
        }
    }


    val metodos = remember {
        mutableStateListOf(
            MetodoPagamentoDefault("Cartão de Crédito", Metodo.CARTAO_CREDITO),
            MetodoPagamentoDefault("Cartão de Débito", Metodo.CARTAO_DEBITO),
            MetodoPagamentoDefault("Dinheiro", Metodo.DINHEIRO),
            MetodoPagamentoDefault("Pix", Metodo.PIX),
        )
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
            viewModel.getProdutos()
        }
    }



    BarButton(sheetContent = {
        if (routes.size <= 0) {
            BarProducts(produtos = produtos, getRouteCallback = getRouteCallback)
        } else {
            BarDirections(rotas = routes, destinationTarget = destination, clearRouter = {
                viewModel.routes.value!!.clear();
            })
        }


    }) {
        Header {
            Row(
                horizontalArrangement = Arrangement.Center, modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(0.dp, 10.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Input(
                            value = filter.nome ?: "",
                            onValueChange = { setFilter(filter.copy(nome = it)) },
                            modifier = Modifier.fillMaxWidth(0.7f)
                        )
                        Button(onClick = { openFilter.value = true }) {
                            Image(
                                modifier = Modifier
                                    .width(14.dp)
                                    .height(14.dp),
                                painter = painterResource(id = R.mipmap.settings),
                                contentDescription = "Adjust"
                            )
                        }
                    }
                }

            }


            Mapa(
                originCoordinates = latLong,
                destinationCoordinates = destination.coordinates,
                filter = viewModel.filterMapa.value!!
            )
            ModalBottomSheet(
                setShowBar = {
                    openFilter.value = it
                },
                showBar = openFilter.value
            ) {

                Column {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Title(content = "Distancia", maxLines = 1)
                        Text(text = filter.distancia.toString())
                    }
                    Row {
                        Slider(
                            valueRange =1f..100f ,
                            value = filter.distancia ?: 0F,
                            onValueChange = { setFilter(filter.copy(distancia = it)) }
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Title(content = "Método de pagamento", maxLines = 1)
                    }


                    LazyColumn() {
                        items(metodos) { item ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(text = item.nome)
                                RadioButton(
                                    selected = filter.metodoPagamento == item.nome,
                                    onClick = {
                                        setFilter(filter!!.copy(metodoPagamento = item.nome))
                                    })

                            }
                        }
                    }
                    Row {
                        Button(
                            onClick = { viewModel.applyFilters() },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Subtitle(content = "Aplicar Filtros", fontSize = 18.sp)
                        }
                    }
                    Row {
                        Button(
                            onClick = { viewModel.clearFilter() },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Subtitle(content = "Limpar Filtros", fontSize = 18.sp)
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun Mapa(originCoordinates: LatandLong, destinationCoordinates: LatandLong?, filter: FilterDTO) {
    val baseUrl = BuildConfig.HOST_WEB
    var destinationString = ""
//    val filters = "${if (filter.distancia != null) "&ditancia=" + filter.distancia else ""}"
//    "${if (filter.metodoPagamento != null) "&metodoPagamento=" + filter.metodoPagamento else ""}"
//    "&${if (filter.nome != null) "&nome=" + filter.nome else ""}"
    if (destinationCoordinates != null) {
        destinationString =
            "&latitudeDestination=${destinationCoordinates.latitude}&longitudeDestination=${destinationCoordinates.longitude}"
    }
    var mUrl =
        "${baseUrl}/mapa/mobile?latitudeOrigin=${originCoordinates.latitude}&longitudeOrigin=${originCoordinates.longitude}"

//    mUrl += destinationString;
//    mUrl += filters

    Column(modifier = Modifier.height(9000.dp)) {
        AndroidView(factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
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
fun BarDirections(rotas: List<RoutesMapper>, destinationTarget: DestinationTarget,clearRouter:()->Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(onClick = { clearRouter()}) {
                Image(painter = painterResource(id = R.mipmap.left), contentDescription = "dsdsds")
            }
            Row {
                destinationTarget.estabelecimento?.nome?.let { Title(content = it, maxLines = 1) }
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

@RequiresApi(Build.VERSION_CODES.O)
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
                            it.estabelecimento?.tempoBike,
                            it.estabelecimento?.tempoPessoa
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


