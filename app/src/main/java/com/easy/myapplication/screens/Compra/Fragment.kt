package com.easy.myapplication.screens.Compra

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProgressBar(etapaAtual: Int, totalEtapa: Int) {
    val activeColor = Color(0xFFDD8400)
    val inactiveColor = Color.Transparent
    val activeTextColor = Color.White
    val inactiveTextColor = Color(0xFF014D5C)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 1..totalEtapa) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    CircleNumber(
                        number = i,
                        isSelected = i <= etapaAtual,
                        activeColor = activeColor,
                        inactiveColor = inactiveColor,
                        activeTextColor = activeTextColor,
                        inactiveTextColor = inactiveTextColor
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = when (i) {
                            1 -> "Dados Gerais"
                            2 -> "Endereço e Contato"
                            3 -> "Criar Senha"
                            else -> ""
                        },
                        fontSize = 11.sp,
                        color = if (i <= etapaAtual) Color.White else Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

            }
        }

    }
}

@Composable
fun CircleNumber(
    number: Int,
    isSelected: Boolean,
    activeColor: Color,
    inactiveColor: Color,
    activeTextColor: Color,
    inactiveTextColor: Color
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(40.dp)
    ) {
        Canvas(modifier = Modifier.size(40.dp)) {
            if (isSelected) {
                drawCircle(
                    color = activeColor,
                    radius = size.minDimension / 2,
                )
            } else {
                drawCircle(
                    color = inactiveTextColor,
                    radius = size.minDimension / 2,
                    style = Stroke(width = 1.dp.toPx())
                )
            }
        }
        Text(
            text = number.toString(),
            color = if (isSelected) activeTextColor else inactiveTextColor,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun OptionButton(text:String){
    Row {
        RadioButton(
            selected = false,
            onClick = { /*TODO*/ }
        )
        Text(
            text = text,
            modifier = Modifier
                .padding(top = 12.dp)
        )
    }

}