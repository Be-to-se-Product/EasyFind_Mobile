package com.easy.myapplication.screens.Compra

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easy.myapplication.ui.theme.Primary
import com.easy.myapplication.utils.generateQRCode


@Composable
fun Buy() {
    val currentStep = remember { mutableStateOf(1) }
    val totalSteps = 3
    val viewModel = remember { CompraViewModel() }
    val selectedOption = remember { mutableStateOf<String?>(null) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(35.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProgressBar(currentStep.value, totalSteps)
            Spacer(modifier = Modifier.height(16.dp))
            when (currentStep.value) {
                1 -> StepOne(selectedOption) { selectedOption.value = it }
                2 -> StepTwo(
                    selectedOption,
                    viewModel = viewModel,
                    onOptionSelected = { selectedOption.value = it })

                3 -> StepThree()
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                if (currentStep.value > 1) {
                    Button(
                        onClick = { if (currentStep.value > 1) currentStep.value-- },
                        colors = ButtonDefaults.buttonColors(Primary)
                    ) {
                        Text("Voltar")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Button(
                    onClick = { if (currentStep.value < totalSteps) currentStep.value++ },
                    colors = ButtonDefaults.buttonColors(Primary)
                ) {
                    Text("Continuar")
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFFCFCFC), RoundedCornerShape(10.dp))
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { /*TODO: Handle cancel action*/ },
                colors = ButtonDefaults.buttonColors(Primary)
            ) {
                Text("Cancelar")
            }
            Text(text = "Total : R$1529,95")
        }
    }
}


@Composable
fun StepOne(selectedOption: MutableState<String?>, onOptionSelected: (String) -> Unit) {
    Text(
        text = "Como deseja realizar o pagamento?",
        modifier = Modifier.fillMaxWidth(),
    )
    Spacer(modifier = Modifier.height(16.dp))
    SelectableOptionButton(
        text = "Pague aqui e retire na loja",
        isSelected = selectedOption.value == "Pague aqui e retire na loja"
    ) { onOptionSelected("Pague aqui e retire na loja") }

    Spacer(modifier = Modifier.height(8.dp))
    SelectableOptionButton(
        text = "Pagamento no estabelecimento",
        isSelected = selectedOption.value == "Pagamento no estabelecimento"
    ) { onOptionSelected("Pagamento no estabelecimento") }
}

@Composable
fun StepTwo(
    selectedOption: MutableState<String?>,
    onOptionSelected: (String) -> Unit,
    viewModel: CompraViewModel = CompraViewModel()
) {
    viewModel.getMetodos()

    val metodosPagamento = viewModel.metodosPagamento.observeAsState()

    Text(
        text = "Selecione o método de pagamento",
        modifier = Modifier.fillMaxWidth(),
    )
    Spacer(modifier = Modifier.height(16.dp))

    metodosPagamento.value?.forEach { metodoPagamento ->
        SelectableOptionButton(
            text = metodoPagamento.descricao!!,
            isSelected = selectedOption.value == metodoPagamento.descricao,
            onClick = { onOptionSelected(metodoPagamento.descricao) }
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}
@Composable
fun StepThree() {
    val qrCodeBitmap = generateQRCode("https://www.google.com")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Escaneie este código para pagar", fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))

        Text("1. Acesse seu Internet Banking ou app de pagamentos.", fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))

        Text("2. Escolha pagar via Pix.", fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))

        Text("3. Escaneie o seguinte código QR.", fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))

        Image(bitmap = qrCodeBitmap.asImageBitmap(), contentDescription = "QR Code")
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Ou copie este código QR para fazer o pagamento pelo app de pagamentos.:",
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(Primary)
        ) {
            Text("Copiar", fontSize = 14.sp)
        }
    }
}
