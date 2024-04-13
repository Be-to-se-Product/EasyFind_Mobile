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
    }
}


@Composable
fun BarButtonsProducts(){


}