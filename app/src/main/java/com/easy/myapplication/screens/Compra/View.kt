package com.easy.myapplication.screens.Compra

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easy.myapplication.LocalNavController
import com.easy.myapplication.screens.Compra.Integration.ItemVenda
import com.easy.myapplication.screens.Compra.Integration.MetodoPagamento
import com.easy.myapplication.screens.Compra.Integration.PedidoCadastro
import com.easy.myapplication.screens.Produto.ProdutoPedido
import com.easy.myapplication.shared.Header.Header
import com.easy.myapplication.ui.theme.Primary
import com.easy.myapplication.utils.generateQRCode


@Composable
fun Buy() {
    val navController= LocalNavController.current
    val currentStep = remember { mutableIntStateOf(1) }
    val totalSteps = 3
    val viewModel = remember { Model() }
    val selectedOption = remember { mutableStateOf<String?>(null) }
    val isFinalStep = remember { mutableStateOf(false) }

    val products =
        navController.previousBackStackEntry?.savedStateHandle?.get<ProdutoPedido>("PRODUTO")
    val total = products?.quantidade!! * products.preco!!
    val quantity = products.quantidade
    val productId = products.id
    val storeId = products.idEstabelecimento
    val isPaymentOnline = remember { mutableStateOf(false) }
    val paymentMethodId = remember { mutableLongStateOf(2L) }

    LaunchedEffect(key1 = storeId) {
        if (storeId != null) {
            viewModel.getMetodos(storeId)
        }
    }

    Header {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(35.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProgressBar(currentStep.value, totalSteps)
                Spacer(modifier = Modifier.height(16.dp))
                when (currentStep.value) {
                    1 -> StepOne(selectedOption, isPaymentOnline) { selectedOption.value = it }
                    2 -> StepTwo(
                        selectedOption,
                        paymentMethodId,
                        viewModel = viewModel,
                        onOptionSelected = { selectedOption.value = it }
                    )

                    3 -> {
                        if (selectedOption.value == "Pix" && !isFinalStep.value) {
                            StepThree()
                        } else {
                            isFinalStep.value = true
                            FinalStep()
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    if (currentStep.value > 1 && !isFinalStep.value) {
                        Button(
                            onClick = { if (currentStep.value > 1) currentStep.value-- },
                            colors = ButtonDefaults.buttonColors(Primary)
                        ) {
                            Text("Voltar")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    if (!isFinalStep.value) {
                        Button(
                            onClick = {
                                if (currentStep.value < totalSteps) {
                                    currentStep.value++
                                    if (currentStep.value == totalSteps && selectedOption.value != "pix") {
                                        sendRequest(
                                            storeId!!,
                                            productId!!,
                                            quantity,
                                            paymentMethodId.value,
                                            isPaymentOnline,
                                            viewModel
                                        )
                                    }
                                } else {
                                    if (selectedOption.value == "pix") {
                                        sendRequest(
                                            storeId!!,
                                            productId!!,
                                            quantity,
                                            paymentMethodId.value,
                                            isPaymentOnline,
                                            viewModel
                                        )
                                        isFinalStep.value = true
                                    }
                                    currentStep.value = totalSteps
                                }
                            },
                            colors = ButtonDefaults.buttonColors(Primary)
                        ) {
                            Text(if (currentStep.value == totalSteps) "Finalizar" else "Continuar")
                        }
                    }
                }
            }
            if (!isFinalStep.value) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color(0xFFFCFCFC), RoundedCornerShape(4.dp))
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { navController.popBackStack()},
                        colors = ButtonDefaults.buttonColors(Primary)
                    ) {
                        Text("Cancelar")
                    }
                    val totalFormatado = String.format("%.2f", total)
                    Text(text = "Total : R$$totalFormatado")

                }
            }
        }
    }
}


@Composable
fun StepOne(
    selectedOption: MutableState<String?>,
    isPaymentOnline: MutableState<Boolean>,
    onOptionSelected: (String) -> Unit
) {
    Text(
        text = "Como deseja realizar o pagamento?",
        modifier = Modifier.fillMaxWidth(),
    )
    Spacer(modifier = Modifier.height(16.dp))
    SelectableOptionButton(
        text = "Pague aqui e retire na loja",
        isSelected = selectedOption.value == "Pague aqui e retire na loja"
    ) {
        onOptionSelected("Pague aqui e retire na loja")
        isPaymentOnline.value = true
    }

    Spacer(modifier = Modifier.height(8.dp))
    SelectableOptionButton(
        text = "Pagamento no estabelecimento",
        isSelected = selectedOption.value == "Pagamento no estabelecimento"
    ) {
        onOptionSelected("Pagamento no estabelecimento")
        isPaymentOnline.value = false
    }
}


@Composable
fun StepTwo(
    selectedOption: MutableState<String?>,
    paymentMethodId: MutableState<Long>,
    onOptionSelected: (String) -> Unit,
    viewModel: Model = Model()
) {



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
            onClick = {
                onOptionSelected(metodoPagamento.descricao)
                paymentMethodId.value = metodoPagamento.id!!
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}


@Composable
fun StepThree() {
    val qrCodeBitmap = generateQRCode("https://www.google.com")
    var codigoPix by remember { mutableStateOf(gerarCodigoPix()) }

    Column {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp)
        ) {
            Text("Escaneie este código para pagar", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Text("1. Acesse seu Internet Banking ou app de pagamentos.", fontSize = 13.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Text("2. Escolha pagar via Pix.", fontSize = 13.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Text("3. Escaneie o seguinte código QR.", fontSize = 13.sp)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)

        ) {
            Image(bitmap = qrCodeBitmap.asImageBitmap(), contentDescription = "QR Code")
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        // CAMPO CÓDIGO PIX
        val context = LocalContext.current
        Column {
            Text(
                "Ou copie e cole este código QR para fazer o pagamento via app de pagamentos ou Internet Banking:",
                fontSize = 14.sp
            )

            // Campo cinza com o número gerado
            Row {
                Text(
                    text = codigoPix,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .background(color = Color(0xFFC1C1C1), shape = RoundedCornerShape(4.dp))
                )

                Spacer(modifier = Modifier.height(15.dp))

                Button(
                    onClick = {
                        copyToClipboard(context, codigoPix)
                        Toast.makeText(
                            context,
                            "Código Pix copiado!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }, modifier = Modifier
                        .padding(8.dp)
                        .height(40.dp)
                        .background(
                            color = Color(0xFFC1C1C1),
                            shape = RoundedCornerShape(4.dp)
                        )
                ) {
                    Text(
                        text = "Copiar",
                    )
                }
            }

            Column {
                Text(
                    text = "Feito isso, basta acompanhar o status do seu pedido clicando no botão abaixo:",
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
fun FinalStep() {

    val navigation = LocalNavController.current;
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                Icons.Filled.ShoppingCart,
                contentDescription = "Pedido feito com sucesso",
                tint = Color.White,
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = "Pedido feito com sucesso!",
                color = Color.White,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Seu pedido será enviado para aprovação do comerciante. Clique no botão abaixo.",
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(Primary),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    text = "Acompanhar o pedido",
                    color = Color.Black
                )
            }
            Button(
                onClick = {
                          navigation.navigate("Mapa")
                },
                colors = ButtonDefaults.buttonColors(Primary),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(
                    text = "Voltar para a página inicial",
                    color = Color.Black
                )
            }
        }
    }
}

fun gerarCodigoPix(): String {
    return (10000000..99999999).random().toString()
}

fun copyToClipboard(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Código Pix", text)
    clipboard.setPrimaryClip(clip)
}

fun sendRequest(
    storeId: Long,
    productId: Long,
    quantity: Int,
    paymentMethodId: Long,
    isPaymentOnline: MutableState<Boolean>,
    viewModel: Model
) {
    val pedido = PedidoCadastro().apply {
        idEstabelecimento = storeId
        itens = listOf(ItemVenda().apply {
            idProduto = productId
            quantidade = quantity
        })
        metodo = MetodoPagamento().apply {
            idMetodoPagamento = paymentMethodId
            isPagamentoOnline = isPaymentOnline.value
        }
        origem = "Tela compra"
    }
    viewModel.postPedido(pedido)
}