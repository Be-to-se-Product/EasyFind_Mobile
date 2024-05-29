package com.easy.myapplication.screens.Produto


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import com.easy.myapplication.LocalNavController
import com.easy.myapplication.dto.AvaliacaoCadastrar
import com.easy.myapplication.shared.ButtonQuantidadeProduto.ProdutoQuantityButton
import com.easy.myapplication.shared.StarRatingBar.StarRatingBar
import com.easy.myapplication.shared.Subtitle.Subtitle
import com.easy.myapplication.shared.Title.Title
import com.easy.myapplication.ui.theme.Primary
import com.easy.myapplication.utils.LocationCallback
import com.easy.myapplication.utils.conversorTime
import com.easy.myapplication.utils.getLatLong


@Composable
fun Produto(view: ProdutoViewModel, id: String?) {
    val navController = LocalNavController.current;
    val context = LocalContext.current
    val setlatLong = view.latLong
    val produtoVenda = view.produtoVenda.observeAsState().value!!
    val setProduto = { it:ProdutoPedido->
        view.produtoVenda.postValue(it)
    }
    val latLong = view.latLong.observeAsState().value!!;
    val produto = view.produto.observeAsState().value!!;


    val locationCallback = object : LocationCallback {
        override fun onSuccess(latitude: Double, longitude: Double) {
            setlatLong.postValue(latLong.copy(latitude, longitude))
        }

        override fun onError(message: String?) {
            print(message)
        }
    }



    getLatLong(context, locationCallback)
    LaunchedEffect(key1 = latLong.latitude){
        if (id != null) {
            view.getProdutoById(id.toLong(),latLong.latitude,latLong.longitude)
        }
    }


    Header{
        Row{
            Column {
                PhotoComponent()
            }

            Column(modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    produto.estabelecimento?.nome?.let {
                        Title(
                            content = it,
                            fontSize = 20.sp,
                            color = Primary,
                            maxLines = 1
                        )
                    }
                    produto.nome?.let { Title(content = it, fontSize = 24.sp, maxLines = 1) }
                    Subtitle(
                        content = produto.descricao,
                        fontSize = 15.sp
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    RouteProduto(view)
                }

                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Title(content = "R$ " + produto.precoAtual.toString(), maxLines = 1)

                }

                produtoVenda.quantidade?.let {
                    ProdutoQuantityButton(
                        quantity = it, onIncrement = {
                            setProduto(
                                produtoVenda.copy(
                                    quantidade = produtoVenda.quantidade?.plus(
                                        1
                                    )
                                )
                            )
                        }, onDecrement = {
                            if (produtoVenda.quantidade > 0) {

                                setProduto(produtoVenda.copy(quantidade = produtoVenda.quantidade - 1))

                            }
                        }
                    )
                }

                Column(
                    modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFFFCA622)),
                        onClick = {
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                "PRODUTO",
                                produtoVenda
                            )
                            navController.navigate("RealizarPedido")
                        }
                    ) {
                        Text(text = "Comprar")
                    }
                }

                Column(
                    modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFFFCA622)),
                        onClick = {

                        }) {
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
                            produto.estabelecimento?.nome?.let { Title(content = it, maxLines = 1) }
                            Subtitle(content = produto.estabelecimento?.segmento)
                        }
                    }

                    produtoVenda.id?.let { ComentarioSection(view, it) }
                }

                LazyColumn(modifier = Modifier.height(650.dp)) {
                    items(items = produto.avaliacao.reversed(),
                        itemContent = {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                it.usuario?.let { it1 -> Title(content = it1, maxLines = 1) }
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

@Composable
fun RouteProduto(view: ProdutoViewModel){
    val produtoTempo = view.produto

    Row(horizontalArrangement = Arrangement.Center) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        )
        {
            IconWithTime(icon = R.mipmap.a_pe, time = conversorTime(produtoTempo.value?.estabelecimento?.tempoPessoa?:0.0))
        }

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            IconWithTime(icon = R.mipmap.carro, time = conversorTime(produtoTempo.value?.estabelecimento?.tempoCarro?:0.0))
        }

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        )
        {
            IconWithTime(icon = R.mipmap.bicicleta, time = conversorTime(produtoTempo.value?.estabelecimento?.tempoBike?:0.0))
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
            modifier = Modifier.size(12.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(time,color = Color(0xFFFCA622))
    }
}

@Composable
fun ComentarioSection(view: ProdutoViewModel, id:Long) {
    val (avaliacao,setAvaliacao) = remember {
       mutableStateOf(AvaliacaoCadastrar(produto = id))
    };

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Title(content = "Comentário", maxLines = 1)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            avaliacao.qtdEstrela.let {
                StarRatingBar(
                    rating = it,
                    onRatingChanged = {
                        setAvaliacao(avaliacao.copy(qtdEstrela = it))
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
                onValueChange = { newValue -> setAvaliacao(avaliacao.copy(comentario = newValue)) },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                modifier = Modifier
                    .border(1.dp, color = Color(0xFFFCA622), shape = RoundedCornerShape(5.dp))
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(4.dp)

            )
        }

        Column(modifier = Modifier.padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFFCA622)),
                onClick = { view.cadastroAvalicao(avaliacaoCadastrar = avaliacao) }) {
                Text(text = "Postar")
            }
        }
    }
}

@Composable
fun PhotoComponent() {
    val selectedImageState = remember { mutableStateOf(R.mipmap.fone) }

    Column {
        Row {
            ImageColumn(listOf(R.mipmap.imagem1, R.mipmap.imagem2, R.mipmap.imagem3), selectedImageState)
            Spacer(modifier = Modifier.width(16.dp))
            MainImageColumn(selectedImageState)
        }
    }
}

@Composable
fun ImageColumn(images: List<Int>, selectedImageState: MutableState<Int>) {
    Column {
        images.forEach { image ->
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier.clickable {
                    selectedImageState.value = image
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun MainImageColumn(selectedImageState: MutableState<Int>) {
    Image(
        painter = painterResource(id = selectedImageState.value),
        contentDescription = null,
        modifier = Modifier.fillMaxWidth()
    )
}
