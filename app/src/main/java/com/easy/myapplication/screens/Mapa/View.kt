package com.easy.myapplication.screens.Mapa

import DestinationTarget
import LatandLong
import MapaViewModel
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.easy.myapplication.BuildConfig
import com.easy.myapplication.LocalNavController
import com.easy.myapplication.R
import com.easy.myapplication.dto.FilterDTO
import com.easy.myapplication.dto.Metodo
import com.easy.myapplication.dto.Produto
import com.easy.myapplication.dto.TargetRoutes
import com.easy.myapplication.shared.BarButton.BarButton
import com.easy.myapplication.shared.Button.Button
import com.easy.myapplication.shared.Header.Header
import com.easy.myapplication.shared.ModalBottomSheet.ModalBottomSheet
import com.easy.myapplication.shared.ProductItem.DataProductItem
import com.easy.myapplication.shared.ProductItem.ProductItem
import com.easy.myapplication.shared.ProductItem.Time
import com.easy.myapplication.shared.ScreenLoading
import com.easy.myapplication.shared.SearchBar.SearchBar
import com.easy.myapplication.shared.Subtitle.Subtitle
import com.easy.myapplication.shared.Title.Title
import com.easy.myapplication.ui.theme.Primary
import com.easy.myapplication.utils.LocationCallback
import com.easy.myapplication.utils.conversorDistancia
import com.easy.myapplication.utils.mediaAvaliacao
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState


data class MetodoPagamentoDefault(
    val nome: String,
    val key: Metodo
)


@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Mapa(viewModel: MapaViewModel) {
    val navController = LocalNavController.current;
    val produtos = viewModel.produtos.observeAsState().value!!
    val filter = viewModel.filterMapa.observeAsState().value!!
    val destination = viewModel.destination.observeAsState().value!!
    val infoRoutes = viewModel.infoRoutes.observeAsState().value!!
    val context = LocalContext.current
    val latLong = viewModel.latLong.observeAsState()
    val navigate = { id: Long ->
        navController.navigate("Produto/${id}")
    }
    val loading = viewModel.isLoading.observeAsState();

    val getRouteCallback = object : GetRouteCallback {
        override fun getRoute(destination: DestinationTarget) {
            viewModel.getRoute(destination, latLong.value!!)
        }
    }

    LaunchedEffect(key1 = latLong.value) {
        if (latLong.value!=null) {
            viewModel.getProdutos()
        }
    }




    ScreenLoading(isLoading = loading.value?.show?:false, text = loading.value?.message?:"")

    Header() {
        Text(text = latLong.value?.latitude.toString()?:"")
            BarButton(sheetContent = {
            if (infoRoutes.routes.size <= 0) {
                BarProducts(
                    produtos = produtos,
                    getRouteCallback = getRouteCallback,
                    navigate = navigate
                )
            } else {
                BarDirections(
                    infoRotas = infoRoutes,
                    destinationTarget = destination,
                    clearRouter = {
                        viewModel.infoRoutes.value!!.routes.clear()
                    })
            }

        }) {
            Filters(viewModel = viewModel, filter = filter)
            ContentMapa(
                originCoordinates = latLong.value?:LatandLong(),
                destinationCoordinates = destination.coordinates,
                filter = filter
            )
            RequestLocationPermission(onPermissionDenied = {
                Log.e("Foi","sds")
            },
                onPermissionGranted = {
                   viewModel.getLocations(context)
                },

                onPermissionsRevoked = {
                    Log.e("sdsd","sddsd")
                }



            )
        }
    }
    }



@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLocationPermission (
    onPermissionGranted: () -> Unit ,
    onPermissionDenied: () -> Unit ,
    onPermissionsRevoked: () -> Unit
) {
    val permissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    LaunchedEffect(key1 = permissionState) {


        val permissionsToRequest = permissionState.permissions.filter {
            !it.status.isGranted
        }
        val allPermissionsRevoked =
            permissionState.permissions.size == permissionState.revokedPermissions.size

        if (permissionsToRequest.isNotEmpty()) permissionState.launchMultiplePermissionRequest()

        if (allPermissionsRevoked) {
            onPermissionsRevoked()
        } else {
            if (permissionState.allPermissionsGranted) {
                onPermissionGranted()
            } else {
                onPermissionDenied()
            }
        }
    }
}



@Composable
fun Filters(filter: FilterDTO, viewModel: MapaViewModel) {
    val (openFilter, setOpenFilter) = remember { mutableStateOf(false) }
    val (filterMapper, setFilterMapper) = remember { mutableStateOf(FilterDTO()) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .zIndex(10f)
            .padding(0.dp, 10.dp)
            .background(Color.Transparent)

    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    Box(
                        modifier = Modifier
                            .zIndex(1F)
                            .absolutePadding(left = 238.dp, top = 9.dp)
                    ) {
                        IconButton(
                            onClick = {
                                viewModel.applyFilters(filter.copy(nome = filterMapper.nome))
                            }, modifier = Modifier
                                .width(16.dp)
                                .height(16.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.mipmap.search),
                                contentDescription = "Icone de busca",
                                modifier = Modifier

                            )
                        }
                    }
                    SearchBar(
                        setValue = {
                            setFilterMapper(filterMapper.copy(nome = it))
                        },

                        modifier = Modifier
                            .fillMaxWidth(0.7f),
                        onSearch = {
                            viewModel.applyFilters(filterMapper)
                        },
                        value = filterMapper.nome ?: ""

                    )

                }
                Row(modifier = Modifier.width(60.dp)) {


                    Button(onClick = { setOpenFilter(true) }, content = {
                        Image(
                            modifier = Modifier
                                .width(14.dp)
                                .height(14.dp),
                            painter = painterResource(id = R.mipmap.settings),
                            contentDescription = "Adjust"
                        )

                    })
                }


            }
        }
    }
    ModalFilter(
        closeFilter = { setOpenFilter(false) },
        showFilter = openFilter,
        filter = filterMapper,
        setFilter = setFilterMapper,
        viewModel = viewModel
    )
}


@Composable
fun ModalFilter(
    closeFilter: () -> Unit,
    showFilter: Boolean,
    filter: FilterDTO,
    setFilter: (FilterDTO) -> Unit,
    viewModel: MapaViewModel
) {
    val slider = remember {
        mutableFloatStateOf(filter.distancia ?: 50F)
    }

    val metodos = listOf(
        MetodoPagamentoDefault("Cartão de Crédito", Metodo.CARTAO_CREDITO),
        MetodoPagamentoDefault("Cartão de Débito", Metodo.CARTAO_DEBITO),
        MetodoPagamentoDefault("Dinheiro", Metodo.DINHEIRO),
        MetodoPagamentoDefault("Pix", Metodo.PIX),
    )

    ModalBottomSheet(
        closeFilter = closeFilter,
        showBar = showFilter
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
                    valueRange = 1f..100f,
                    value = slider.floatValue,
                    onValueChange = { slider.floatValue = it },
                    colors = SliderDefaults.colors(
                        thumbColor = Primary,
                        activeTickColor = Primary,
                        activeTrackColor = Primary
                    ),
                    onValueChangeFinished = {
                        setFilter(filter.copy(distancia = slider.floatValue))
                    }
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Title(content = "Método de pagamento", maxLines = 1)
            }


            LazyColumn {
                items(metodos) { item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = item.nome)
                        RadioButton(
                            selected = filter.metodoPagamento == item.nome,
                            colors = RadioButtonColors(
                                selectedColor = Primary,
                                unselectedColor = Color.White,
                                disabledSelectedColor = Color.Gray,
                                disabledUnselectedColor = Color.Gray
                            ),
                            onClick = {
                                setFilter(filter.copy(metodoPagamento = item.nome))
                            })

                    }
                }
            }



            Row {
                Button(
                    onClick = { viewModel.applyFilters(filter) },
                    content = {
                        Subtitle(content = "Aplicar Filtros", fontSize = 18.sp)
                    }
                )
            }

            Row {
                Button(
                    onClick = { viewModel.clearFilter(setFilter) },
                    content = {
                        Subtitle(content = "Limpar Filtros", fontSize = 18.sp)
                    }
                )
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ContentMapa(
    originCoordinates: LatandLong,
    destinationCoordinates: LatandLong?,
    filter: FilterDTO
) {
    val baseUrl = BuildConfig.HOST_WEB
    var destinationString = ""
    var filters = if (filter.distancia != null) "&distance=" + filter.distancia else ""
    filters += if (filter.metodoPagamento != null) "&paymentMethod=" + filter.metodoPagamento else ""
    filters += if (filter.nome != null) "&name=" + filter.nome else ""
    if (destinationCoordinates != null) {
        destinationString =
            "&latitudeDestination=${destinationCoordinates.latitude}&longitudeDestination=${destinationCoordinates.longitude}"
    }
    var mUrl =
        "${baseUrl}/mapa/mobile?latitudeOrigin=${originCoordinates.latitude}&longitudeOrigin=${originCoordinates.longitude}"

    mUrl += destinationString
    mUrl += filters

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
    Column {
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
fun BarDirections(
    infoRotas: TargetRoutes,
    destinationTarget: DestinationTarget,
    clearRouter: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(onClick = { clearRouter() }) {
                Image(painter = painterResource(id = R.mipmap.left), contentDescription = "dsdsds")
            }
            Row {
                destinationTarget.estabelecimento?.nome?.let { Title(content = it, maxLines = 1) }
            }
            Row {
                Subtitle(content = conversorDistancia(infoRotas.distance), color = Primary)
            }
        }


        LazyColumn(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            items(items = infoRotas.routes, itemContent = {
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
fun BarProducts(
    produtos: List<Produto>,
    getRouteCallback: GetRouteCallback,
    navigate: (Long) -> Unit
) {

    Column {

        if (produtos.isNotEmpty()) {
            LazyColumn {
                items(items = produtos, itemContent = {
                    ProductItem(
                        getRouteCallback = getRouteCallback,
                        navigate = navigate,
                        data = DataProductItem(
                            id = it.id ?: 0L,
                            name = it.nome.toString(),
                            qtdStars = mediaAvaliacao(it.avaliacao),
                            shop = it.estabelecimento?.nome ?: "",
                            price = it.precoAtual ?: 0.0,
                            time = Time(
                                it.estabelecimento?.tempoCarro,
                                it.estabelecimento?.tempoBike,
                                it.estabelecimento?.tempoPessoa
                            ),
                            imagens = if(it.imagens?.size!! > 0)   it.imagens[0] else "",
                            latitude = it.estabelecimento?.endereco?.latitude,
                            longitude = it.estabelecimento?.endereco?.longitude,
                            estabelecimento = it.estabelecimento
                        )
                    )
                })
            }
        } else {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Title(content = "Não há produtos nessa região")
            }

        }
    }
}




