package com.easy.myapplication.screens.Login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.easy.myapplication.dto.ConsumidorCriacaoDTO
import com.easy.myapplication.dto.UsuarioCriacaoDTO
import com.easy.myapplication.shared.Input.Input
import com.easy.myapplication.shared.Input.Type
import com.easy.myapplication.shared.SelectBox.SelectBox
import com.easy.myapplication.shared.Title.Title
import com.easy.myapplication.utils.formatarData

@Composable
fun login(model: Model,navController: NavController){
    val (usuario, usuarioSetter) = remember { mutableStateOf(UsuarioCriacaoDTO()) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(0.8f)
    ) {
        Input(value = usuario.email?:"", onValueChange = {usuarioSetter(usuario.copy(email = it))},label="Email")
        Spacer(modifier = Modifier.height(30.dp))
        Input(value = usuario.senha?:"", onValueChange = {usuarioSetter(usuario.copy(senha = it))}, type = Type.PASSWORD, label = "Senha")
        Spacer(modifier = Modifier.height(40.dp))
        Button(onClick = {model.loginUsuario(usuario, { navController.navigate("Mapa") }) }){
            Title(content = "Entrar", fontSize = 14.sp, maxLines = 1)
        }
    }
}
@Composable
fun cadastro(model: Model){
    val (consumidor, consumidorSetter) = remember { mutableStateOf(ConsumidorCriacaoDTO()) }

    val generoState = remember { mutableStateOf(consumidor.genero?:"") }
    val expandido = remember { mutableStateOf(false) }
    val generos = remember {
        mutableStateListOf(
            "Masculino",
            "Feminino",
            "Outros"
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(0.8f)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth(1f)) {
            Column(modifier = Modifier.weight(1f)) {
                Input(value = consumidor.cpf?:"", onValueChange = {consumidorSetter(consumidor.copy(cpf = it))}, type = Type.CPF,label="CPF")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Input(value = consumidor.celular?:"", onValueChange = {consumidorSetter(consumidor.copy(celular = it))}, type = Type.PHONE,label="Telefone")
            }
        }
        Column {
            Input(value = consumidor.nome?:"", onValueChange = {consumidorSetter(consumidor.copy(nome = it))},label="Nome Completo")
        }
        Row(modifier = Modifier.fillMaxWidth(1f)) {
            Column(modifier = Modifier.weight(1f)) {
                Input(value = consumidor.dataNascimento ?: "", onValueChange = { newValue ->
                    val formattedDate = formatarData(newValue)
                    consumidorSetter(consumidor.copy(dataNascimento = formattedDate))
                }, type = Type.DATE,label="Data de Nascimento")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                SelectBox(value = consumidor.genero?:"",
                    onValueChange = {consumidorSetter(consumidor.copy(genero = it))},
                    expandido = expandido,
                    label = "Gênero",
                    generos = generos,
                    genero = generoState
                )
            }
        }
        Column {
            Input(value = consumidor.usuario?.email?:"", onValueChange = { email ->
                val usuarioAtualizado = consumidor.usuario?.copy(email = email) ?: UsuarioCriacaoDTO(email = email)
                consumidorSetter(consumidor.copy(usuario = usuarioAtualizado))
            },label="E-mail")
        }
        Column {
            Input(value = consumidor.usuario?.senha?:"", onValueChange = { senha ->
                val usuarioAtualizado = consumidor.usuario?.copy(senha = senha) ?: UsuarioCriacaoDTO(senha = senha)
                consumidorSetter(consumidor.copy(usuario = usuarioAtualizado))
            }, type = Type.PASSWORD, label="Senha")
        }
        Spacer(modifier = Modifier.height(40.dp))
        Button(onClick = {model.cadastrarUsuario(consumidor)}){
            Title(content = "Cadastrar", fontSize = 14.sp, maxLines = 1)
        }
    }
}


