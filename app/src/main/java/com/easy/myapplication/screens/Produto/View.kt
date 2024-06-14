package com.easy.myapplication.screens.Produto


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import com.easy.myapplication.LocalNavController
import com.easy.myapplication.dto.AvaliacaoCadastrar
import com.easy.myapplication.dto.CarrinhoRequestDTO
import com.easy.myapplication.screens.Carrinho.Model
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
    val setProduto = { it: ProdutoPedido ->
        view.produtoVenda.postValue(it)
    }
    val latLong = view.latLong.observeAsState().value!!;
    val produto = view.produto.observeAsState().value!!;
    var listaProdutosVenda = mutableListOf<ProdutoPedido>()
    val modelCarrinho = Model()



    LaunchedEffect(key1 = Unit) {
        getLatLong(context, onSucess = { latitude, longitude ->
            setlatLong.postValue(latLong.copy(latitude, longitude))
        }, onFailure = { message ->
            Log.e("Errror", message)
        })
    }

    LaunchedEffect(key1 = latLong.latitude) {
        if (id != null) {
            view.getProdutoById(id.toLong(), latLong.latitude, latLong.longitude)
        }
    }


    Header {
        Surface(color = Color(0xFF292929), modifier = Modifier.fillMaxSize()) {

            Row {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    produto.imagens?.let { PhotoComponent(it) }
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

                    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            com.easy.myapplication.shared.Button.Button(
                                onClick = {
                                    listaProdutosVenda.add(produtoVenda)
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "PRODUTO",
                                        listaProdutosVenda
                                    )
                                    navController.navigate("RealizarPedido")
                                }, content = { Text(text = "Comprar") }
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            com.easy.myapplication.shared.Button.Button(
                                onClick = {
                                    val carrinho =
                                        CarrinhoRequestDTO(produtoVenda.quantidade, produtoVenda.id)
                                    modelCarrinho.postCarrinho(carrinho)
                                }, content = { Text(text = "Adicionar ao carrinho") }
                            )
                        }
                    }

                    Column {

                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = produto.estabelecimento?.imagem ?: "",
                                contentDescription = "Mercado",
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(8.dp, 8.dp, 8.dp, 8.dp)),
                                contentScale = ContentScale.Crop,

                                )

                            Column(
                                modifier = Modifier.padding(start = 16.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                produto.estabelecimento?.nome?.let {
                                    Title(
                                        content = it,
                                        maxLines = 1
                                    )
                                }
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
                                    it.qtdEstrela?.toFloat()
                                        ?.let { it1 -> StarRatingBar(rating = it1) }
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
}

@Composable
fun RouteProduto(view: ProdutoViewModel) {
    val produtoTempo = view.produto

    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            IconWithTime(
                icon = R.mipmap.a_pe,
                time = conversorTime(produtoTempo.value?.estabelecimento?.tempoPessoa ?: 0.0)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            IconWithTime(
                icon = R.mipmap.carro,
                time = conversorTime(produtoTempo.value?.estabelecimento?.tempoCarro ?: 0.0)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally

        )
        {
            IconWithTime(
                icon = R.mipmap.bicicleta,
                time = conversorTime(produtoTempo.value?.estabelecimento?.tempoBike ?: 0.0)
            )
        }
    }
}

@Composable
fun IconWithTime(icon: Int, time: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(12.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Subtitle(content = time, color = Color(0xFFFCA622), fontSize = 12.sp)
    }
}

@Composable
fun ComentarioSection(view: ProdutoViewModel, id: Long) {
    val (avaliacao, setAvaliacao) = remember {
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

        Column(
            modifier = Modifier.padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
fun PhotoComponent(images:List<String>) {

    if(images.isEmpty()){
        return Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Column(modifier = Modifier.fillMaxHeight()) {
                            Image(
                                painter = painterResource(id = R.mipmap.default_produto),
                                contentDescription = "",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(275.dp)
                                    .clip(RoundedCornerShape(10.dp))
                            )
                        }
                    }
        }
    }
    val selectedImageState = remember { mutableStateOf(images[0]) }

    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ImageColumn(
                images,
                selectedImageState
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.fillMaxHeight()) {
                MainImageColumn(selectedImageState)
            }
        }
    }
}

@Composable
fun ImageColumn(images: List<String>, selectedImageState: MutableState<String>) {
    Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.height(270.dp)) {

        images.forEach { image ->
            AsyncImage(
                model = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clickable {
                        selectedImageState.value = image
                    }
                    .height(70.dp)
                    .fillMaxWidth(0.2f)
                    .clip(RoundedCornerShape(10.dp))

            )
        }
    }
}

@Composable
fun MainImageColumn(selectedImageState: MutableState<String>) {
    AsyncImage(
        model = selectedImageState.value,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(275.dp)
            .clip(RoundedCornerShape(10.dp))
    )
}
