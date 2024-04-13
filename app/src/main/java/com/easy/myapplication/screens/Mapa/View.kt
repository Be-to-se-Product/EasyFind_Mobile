package com.easy.myapplication.screens.Mapa

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easy.myapplication.R
import com.easy.myapplication.shared.BarButton.BarButton
import com.easy.myapplication.shared.Header.Header
import com.easy.myapplication.shared.Input.Input
import com.easy.myapplication.shared.StarRatingBar.StarRatingBar
import com.easy.myapplication.shared.Subtitle.Subtitle
import com.easy.myapplication.shared.Title.Title
import com.easy.myapplication.ui.theme.Primary


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Mapa() {
    val searchProduct = remember { mutableStateOf("") }
    val (showBar, setShowBar) = remember {
        mutableStateOf(true)
    }
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
        BarButton(setShowBar = setShowBar, showBar = true) {
            BarProducts()

//            BarDirections()
        }

    }
}


@Composable
fun Left(){
    Column() {
        Image(
            painter = painterResource(id = R.mipmap.left),
            contentDescription = "Marcador Inicio",
            modifier = Modifier.size(30.dp)
        )
    }

}


@Composable
fun Right(){
    Column {
        Image(
            painter = painterResource(id = R.mipmap.right),
            contentDescription = "Marcador Inicio",
            modifier = Modifier.size(30.dp)
        )
    }

}


@Composable
fun Siga(){
    Column {
        Image(
            painter = painterResource(id = R.mipmap.up),
            contentDescription = "Marcador Inicio",
            modifier = Modifier.size(40.dp)
        )
    }

}

@Composable
fun Back(){
    Column {
        Image(
            painter = painterResource(id = R.mipmap.bottom),
            contentDescription = "Marcador Inicio",
            modifier = Modifier.size(40.dp)
        )
    }

}
@Composable
@Preview
fun BarDirections() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    Title(content = "Carrefour Express Paulista")
                }
                Row {
                    Subtitle(content = "4min(300m)", color = Primary)
                }
            }

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
                    Left()
                    Column(verticalArrangement = Arrangement.Center) {
                        Subtitle(
                            content = "Inicie seu trajéto e vire a direta a 200 métros",
                            color = Primary
                        )
                    }
                }
            }
        }
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
                    Column {
                        Right()
                    }

                    Column {
                        Subtitle(
                            content = "Inicie seu trajéto e vire a direta a 200 métros",
                            color = Primary
                        )
                    }
                }
            }
        }
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
                    Column {
                        Image(
                            painter = painterResource(id = R.mipmap.marked),
                            contentDescription = "Marcador Inicio",
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Column(verticalArrangement = Arrangement.Center) {
                        Subtitle(
                            content = "Inicie seu trajéto e vire a direta a 200 métros",
                            color = Primary
                        )
                    }
                }
            }
        }


    }

}

@Composable
fun BarProducts() {
    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
                    .background(Color.Red)
                    .width(70.dp)
                    .height(80.dp)
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.fone),
                    contentDescription = "Imagem do produto",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentScale = ContentScale.Crop
                )
            }


            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Title(content = "Fone de ouvido")
                    StarRatingBar(rating = 2F, size = 4F)
                }
                Row {
                    Subtitle(content = "dfdfdf", color = Primary)
                }
                Row {
                    Subtitle(content = "R$ 1220,00", color = Primary)
                }


                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Column {
                                Image(
                                    painter = painterResource(id = R.mipmap.cart),
                                    contentDescription = "Icone",
                                    modifier = Modifier.width(10.dp)
                                )
                            }
                            Column {
                                Subtitle(content = "15 min", color = Primary)
                            }
                        }
                    }
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Column {
                                Image(
                                    painter = painterResource(id = R.mipmap.cart),
                                    contentDescription = "Icone",
                                    modifier = Modifier.width(10.dp)
                                )
                            }
                            Column {
                                Subtitle(content = "15 min", color = Primary)
                            }
                        }
                    }
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Column {
                                Image(
                                    painter = painterResource(id = R.mipmap.cart),
                                    contentDescription = "Icone",
                                    modifier = Modifier.width(10.dp)
                                )
                            }
                            Column {
                                Subtitle(content = "15 min", color = Primary)
                            }
                        }
                    }
                }
            }
        }
    }
}