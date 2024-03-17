package com.easy.myapplication

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easy.myapplication.ui.theme.Primary
import com.easy.myapplication.ui.theme.Seconday


@Composable
@Preview
fun Login() {
    Surface(color = Color(0xFF292929)) {

    val switch = remember { mutableStateOf(true) }
    val expandido = remember { mutableStateOf(false) }
    val email = remember { mutableStateOf("") }
    val cpf = remember { mutableStateOf("") }
    val telefone = remember { mutableStateOf("") }
    val nomeCompleto = remember { mutableStateOf("") }
    val dtNascimento = remember { mutableStateOf("") }
    val genero = remember { mutableStateOf("") }
    val emailCadastro = remember { mutableStateOf("") }
    val senhaCadastro = remember { mutableStateOf("") }
    val generos = remember {
        mutableStateListOf(
            "Masculino",
            "Feminino",
            "Outros"
        )
    }
    val senha = remember {
        mutableStateOf("")
    }

    val animatedButtonEnabled = animateColorAsState(
        targetValue = if (switch.value) Primary else Seconday, animationSpec = tween(
            150, 0,
            LinearEasing
        )
    );

    val animatedButtonDisabled = animateColorAsState(
        targetValue = if (switch.value) Seconday else Primary, animationSpec = tween(
            150, 0,
            LinearEasing
        )
    )

    Column(modifier = Modifier.verticalScroll(rememberScrollState()).padding(PaddingValues(top = 25.dp))) {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(0.3f)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.fillMaxWidth(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = "Tefdfd",
                    modifier = Modifier.fillMaxSize(0.2f)
                )

                Box {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth(1f)
                    ) {
                        Row() {
                            Title(content = "Bem vindo a Easy Find")

                        }
                        Row {
                            Subtitle(content = "Conectando você ao comércio local")
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Divider(
                            Modifier
                                .border(1.dp, Primary)
                                .fillMaxWidth(0.7f)
                        )
                    }
                }
            }
        }

        Box() {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(1f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.5f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                                    .height(30.dp)
                                    .padding(0.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = animatedButtonEnabled.value
                                ),
                                onClick = {
                                    switch.value = !switch.value
                                },
                                shape = RoundedCornerShape(0)
                            ) {
                                Text(text = "Login", fontSize = 11.sp, color = Color.White)
                            }
                        }

                        Column(
                            modifier = Modifier
                                .background(Seconday)
                        ) {
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                                    .height(30.dp)
                                    .padding(0.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = animatedButtonDisabled.value
                                ),
                                onClick = {
                                    switch.value = !switch.value
                                },
                                shape = RoundedCornerShape(0)
                            ) {
                                Text(
                                    text = "Cadastro",
                                    fontSize = 11.sp,
                                    color = if (!switch.value) Color.Black else Color.White,
                                    modifier = Modifier.fillMaxWidth(1f)
                                )
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth(1f)) {
            if (switch.value){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Input(value = email.value, onValueChange = {email.value = it},label="Email")
                    Spacer(modifier = Modifier.height(30.dp))
                    Input(value = senha.value, onValueChange = {senha.value = it }, type = Type.PASSWORD, label = "Senha")
                    Spacer(modifier = Modifier.height(40.dp))
                    ButtonCustomize(onClick = {}){
                        Title(content = "Entrar", fontSize = 14.sp)
                    }
                }
            }else{
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Row(modifier = Modifier
                        .fillMaxWidth(1f)) {
                        Column(modifier = Modifier.weight(1f)) {
                            Input(value = cpf.value, onValueChange = {cpf.value = it},label="CPF")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Input(value = telefone.value, onValueChange = {telefone.value = it},label="Telefone")
                        }
                    }
                    Column {
                        Input(value = nomeCompleto.value, onValueChange = {nomeCompleto.value = it},label="Nome Completo")
                    }
                    Row(modifier = Modifier.fillMaxWidth(1f)) {
                        Column(modifier = Modifier.weight(1f)) {
                            Input(value = dtNascimento.value, onValueChange = {dtNascimento.value = it},label="Data de Nascimento")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            SelectBox(value = genero.value,
                                onValueChange = {genero.value=it},
                                expandido = expandido,
                                label = "Gênero",
                                generos = generos,
                                genero = genero
                            )
                        }
                    }
                    Column {
                        Input(value = emailCadastro.value, onValueChange = {emailCadastro.value = it},label="E-mail")
                    }
                    Column {
                        Input(value = senhaCadastro.value, onValueChange = {senhaCadastro.value = it},label="Senha")
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    ButtonCustomize(onClick = {}){
                        Title(content = "Cadastrar", fontSize = 14.sp)
                    }
                }
            }

        }

    }
    }

}
    
