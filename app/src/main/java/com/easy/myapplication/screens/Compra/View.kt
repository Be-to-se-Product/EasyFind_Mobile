package com.easy.myapplication.screens.Compra

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun Buy(navController: NavController) {
    val currentStep = remember { mutableIntStateOf(1) }
    val totalSteps = 3
    val checked = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(35.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProgressBar(currentStep.intValue, totalSteps)
    }
}

@Composable
fun StepOne() {
    Text(
        text = "Como deseja realizar o pagamento?",
        modifier = Modifier.fillMaxWidth(),
    )
    Spacer(modifier = Modifier.height(16.dp))
    OptionButton(text = "Pague aqui e retire na loja", false)

    Spacer(modifier = Modifier.height(16.dp))
    OptionButton(text = "Pagamento no estabelecimento", false)
}

@Composable
fun StepTwo() {
    Text(
        text = "Selecione o método de pagamento",
        modifier = Modifier.fillMaxWidth(),
    )
    Spacer(modifier = Modifier.height(16.dp))
    OptionButton(text = "Pague aqui e retire na loja", false)

    Spacer(modifier = Modifier.height(16.dp))
    OptionButton(text = "Pagamento no estabelecimento", false)
}
