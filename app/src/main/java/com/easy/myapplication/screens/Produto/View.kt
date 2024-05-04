package com.easy.myapplication.screens.Produto


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easy.myapplication.R
import com.easy.myapplication.shared.Header.Header
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.easy.myapplication.dto.ProdutoVendaDTO
import com.easy.myapplication.shared.StarRatingBar.StarRatingBar
import com.easy.myapplication.shared.Subtitle.Subtitle
import com.easy.myapplication.shared.Title.Title
import com.easy.myapplication.ui.theme.Primary
import com.easy.myapplication.utils.LocationCallback
import com.easy.myapplication.utils.getLatLong


@Composable
fun Produto(view: ProdutoViewModel) {

    var quantity = remember { mutableStateOf(0) }
    val isBuyButtonClicked = remember { mutableStateOf(false) }
    val setlatLong = view.latLong
    val latLong = view.latLong.observeAsState().value!!;
    val produto = view.produto.observeAsState().value!!;
    val context = LocalContext.current
    val locationCallback = object : LocationCallback {
        override fun onSuccess(latitude: Double, longitude: Double) {
            setlatLong.postValue(latLong.copy(latitude, longitude))
        }

        override fun onError(message: String?) {
            print(message)
        }
    }


    LaunchedEffect(key1 = Unit) {
        getLatLong(context, locationCallback)
    }

    LaunchedEffect(key1 = latLong.latitude){
        view.getProdutoById(1,latLong.latitude,latLong.longitude)
    }


    Header{
        Row(
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())) {
                Column(modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.Start) {
                    produto.estabelecimento?.nome?.let {
                        Title(content = it,
                            fontSize = 20.sp,
                            color = Primary)
                    }
                    produto.nome?.let { Title(content = it, fontSize = 24.sp) }
                    Subtitle(content = produto.descricao,
                        fontSize = 15.sp)
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    RouteProduto(view)
                }

                Column(modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Title(content = produto.precoAtual.toString())

                }

                ProdutoQuantityButton(
                   quantity = quantity.value
                    ,onIncrement = { quantity.value++ }
                    , onDecrement = {
                        if (quantity.value > 0){
                            quantity.value--
                        }
                    }
                )



                Column(modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFFFCA622)),
                        onClick = { /*TODO*/ }) {
                        Text(text = "Adicionar no carrinho")
                    }
                }

                Column {

                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.mipmap.mercado),
                            contentDescription = "Mercado",
                            modifier = Modifier.size(60.dp)
                        )

                        Column(
                            modifier = Modifier.padding(start = 16.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            produto.estabelecimento?.nome?.let { Title(content = it) }
                            Subtitle(content = produto.estabelecimento?.segmento)
                        }
                    }

                    ComentarioSection(view)

                    Column(modifier = Modifier.padding(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp),
                            colors = ButtonDefaults.buttonColors(Color(0xFFFCA622)),
                            onClick = { view.cadastroAvalicao(1) }) {
                            Text(text = "Postar")
                        }
                    }
                }

                LazyColumn(modifier = Modifier.height(900.dp)) {
                    items(items = produto.avaliacao,
                        itemContent = {
                            Column(modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.Start) {
                                it.usuario?.let { it1 -> Title(content = it1) }
                                it.qtdEstrela?.toFloat()?.let { it1 -> StarRatingBar(rating = it1) }
                                Column {
                                    Subtitle(content = it.descricao)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

// Função da distancia do mercado
@Composable
fun RouteProduto(view: ProdutoViewModel){
    val produtoTempo = view.produto

    Row(horizontalArrangement = Arrangement.Center) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        )
        {
            IconWithTime(icon = R.mipmap.a_pe, time = produtoTempo.value?.estabelecimento?.tempoPessoa.toString())
        }

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            IconWithTime(icon = R.mipmap.carro, time = produtoTempo.value?.estabelecimento?.tempoCarro.toString())
        }

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        )
        {
            IconWithTime(icon = R.mipmap.bike, time = produtoTempo.value?.estabelecimento?.tempoBike.toString())
        }
    }
}

@Composable
fun IconWithTime(icon: Int, time: String){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(15.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(time,color = Color(0xFFFCA622))
    }
}

@Composable
fun ComentarioSection(view: ProdutoViewModel) {
    val setAvaliacao = view.avaliacao
    val avaliacao = view.avaliacao.observeAsState().value!!;

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Title(content = "Comentário")
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            avaliacao.qtdEstrela?.let {
                StarRatingBar(
                    rating = it,
                    onRatingChanged = {
                        setAvaliacao.postValue(avaliacao.copy(qtdEstrela = it))
                    }
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        avaliacao.comentario?.let {
            TextField(
                value = it,
                onValueChange = { newValue -> setAvaliacao.postValue(avaliacao.copy(comentario = newValue)) },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                modifier = Modifier
                    .border(1.dp, color = Color(0xFFFCA622), shape = RoundedCornerShape(5.dp))
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(4.dp)

            )
        }
    }
}

@Composable
fun ProdutoQuantityButton(
    quantity: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)
            .border(
                width = 1.dp,
                color = Color(0xFFFCA622),
                shape = RoundedCornerShape(8.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        IconButton(
            onClick = {
                if (quantity > 0){
                onDecrement()
                }
            }
        ) {
            Icon(
                painter = painterResource(id = R.mipmap.remover)
                ,contentDescription = "Remover a quantidade"
                ,tint = Color(0xFFFCA622)
            )
        }
        Text(
            text = quantity.toString(),
            style = TextStyle(fontSize = 18.sp),
            fontWeight = FontWeight.Bold
        )

        IconButton(
            onClick = {
                onIncrement()
            }
        ) {
            Icon(
                painter = painterResource(id = R.mipmap.adicionar)
                ,contentDescription = "Adicionar quantidade"
                , tint = Color(0xFFFCA622)
            )
        }
    }
}

@Composable
fun ComprarButton(
    quantidade: Int,
    onClickComprar: (ProdutoVendaDTO) -> Unit,
    view: ProdutoViewModel
){
    val produtoId = view.produto.value?.id
    Column(modifier = Modifier.padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFFCA622)),
            onClick = {
            }
        ) {
            Text(text = "Comprar")

        }
    }
}

