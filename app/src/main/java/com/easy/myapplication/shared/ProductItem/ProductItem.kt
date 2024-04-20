package com.easy.myapplication.shared.ProductItem

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.easy.myapplication.R
import com.easy.myapplication.shared.StarRatingBar.StarRatingBar
import com.easy.myapplication.shared.Subtitle.Subtitle
import com.easy.myapplication.shared.Title.Title
import com.easy.myapplication.ui.theme.Primary


data class Time(
    val car: Long?,
    val bike: Long?,
    val people: Long?
)

data class DataProductItem(
    val name:String,
    val image:String?="",
    val qtdStars: Double,
    val shop:String,
    val price: Double,
    val time : Time?
)

@Composable
fun ProductItem(data:DataProductItem){
    Row(
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
                .width(75.dp)
                .height(85.dp)
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
                Title(content =data.name)
                StarRatingBar(rating = 3F, size = 5F)
            }
            Row {
                Subtitle(content = data.shop, color = Primary)
            }
            Row {
                Subtitle(content ="R$"+data.price.toString(), color = Primary)
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
                            Subtitle(content = (data.time?.bike?:"").toString(), color = Primary)
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
                            Subtitle(content = (if(data.time?.car!=null) data.time.car else "").toString(), color = Primary)
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
                            Subtitle(content = (if(data.time?.people!=null) data.time.people else "").toString(), color = Primary)
                        }
                    }
                }
            }
        }
    }
}