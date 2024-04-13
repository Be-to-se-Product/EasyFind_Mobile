package com.easy.myapplication.screens.Produto

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easy.myapplication.R
import com.easy.myapplication.shared.Header.Header
import com.easy.myapplication.shared.Subtitle.Subtitle

import com.easy.myapplication.shared.Title.Title
import com.easy.myapplication.ui.theme.Primary
import java.sql.Time


@Composable
@Preview
fun Produto(){
    Header{
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
    }

}

@Composable
fun IconWithTimesScreen() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    )
    {  }
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
        Text(text = time)
    }
}