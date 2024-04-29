package com.easy.myapplication.screens.Produto


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easy.myapplication.R
import com.easy.myapplication.shared.Header.Header
import androidx.compose.material3.TextField
import com.easy.myapplication.shared.StarRatingBar.StarRatingBar
import com.easy.myapplication.shared.Subtitle.Subtitle
import com.easy.myapplication.shared.Title.Title
import com.easy.myapplication.ui.theme.Primary


@Composable
@Preview
fun Produto(){
    val star = remember {
        mutableStateOf(0f)
    }

    Header{
        Row(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column {
                Column(modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.Start) {
                    Title(content = "Mercado Bom Preço",
                        fontSize = 20.sp,
                        color = Primary)
                    Title(content = "Fone de Ouvido", fontSize = 24.sp)
                    Subtitle(content = "Lorem Ipsum is simply dummy text of the " +
                            "printing and typesetting industry.  " +
                            "release of Letraset sheets " +
                            "containingPageMaker including versions of Lorem Ipsum.",
                        fontSize = 15.sp)
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    RouteProduto()
                }

                Column(modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Title(content = "R$160,00")

                }

                Column(modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFFFCA622)),
                        onClick = { /*TODO*/ }
                    ) {
                        Text(text = "Comprar")
                    }
                }

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
                            Title(content = "Bom Preço")
                            Subtitle(content = "Mercado")
                        }
                    }

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
                            StarRatingBar(
                                rating = star.value,
                                onRatingChanged = {
                                    star.value = it
                                }
                            )
                        }
                    }

                    ComentarioSection()

                    Column(modifier = Modifier.padding(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp),
                            colors = ButtonDefaults.buttonColors(Color(0xFFFCA622)),
                            onClick = { /*TODO*/ }) {
                            Text(text = "Postar")
                        }
                    }
                }

                Column(modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.Start) {
                    Title(content = "Matheus")
                    StarRatingBar(rating = 2.5f)
                    Column {
                        Subtitle(content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
                    }
                }


                Column(modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.Start) {
                    Title(content = "Matheus")
                    StarRatingBar(rating = 2.5f)
                    Column {
                        Subtitle(content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
                    }
                }
            }
        }
    }
}

// Função da distancia do mercado
@Composable
fun RouteProduto(){
    Row(horizontalArrangement = Arrangement.Center) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        )
        {
            IconWithTime(icon = R.mipmap.a_pe, time = "30m")
        }

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            IconWithTime(icon = R.mipmap.carro, time = "30m")
        }

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        )
        {
            IconWithTime(icon = R.mipmap.bike, time = "30m")
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
        Text(text = time, color = Color(0xFFFCA622))
    }
}

@Composable
fun ComentarioSection() {
    val textFieldValue = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        TextField(
            value = textFieldValue.value,
            onValueChange = { newValue -> textFieldValue.value = newValue},
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            modifier = Modifier
                .border(1.dp, color = Color(0xFFFCA622), shape = RoundedCornerShape(5.dp))
                .fillMaxWidth()
                .height(100.dp)
                .padding(4.dp)

        )
    }
}

