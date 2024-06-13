package com.easy.myapplication.screens.Login

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easy.myapplication.R
import com.easy.myapplication.dto.ConsumidorCriacaoDTO
import com.easy.myapplication.dto.UsuarioCriacaoDTO
import com.easy.myapplication.shared.Button.Button
import com.easy.myapplication.shared.Input.Input
import com.easy.myapplication.shared.Input.Type
import com.easy.myapplication.shared.SelectBox.SelectBox
import com.easy.myapplication.shared.Title.Title
import com.easy.myapplication.utils.formatarData

@Composable
fun login(model: Model,navigation: ()->Unit ){
    val (usuario, usuarioSetter) = remember { mutableStateOf(UsuarioCriacaoDTO()) }
    val message = model.message.observeAsState().value!!;
    val loading = model.loading.observeAsState().value!!;

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(0.8f)
    ) {
        Input(value = usuario.email?:"", onValueChange = {usuarioSetter(usuario.copy(email = it))},label= stringResource(id = R.string.label_email))
        Spacer(modifier = Modifier.height(30.dp))
        Input(value = usuario.senha?:"", onValueChange = {usuarioSetter(usuario.copy(senha = it))}, type = Type.PASSWORD, label = stringResource(id = R.string.label_senha))
        Spacer(modifier = Modifier.height(40.dp))
        Button(onClick = {model.loginUsuario(usuario,navigation) }, isLoading = loading.show, content = {
            Text(text = stringResource(id = R.string.button_entrar))
        })
Spacer(modifier = Modifier.height(10.dp))

            Text(text = message.message, color = Color.White)

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
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(500.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth(1f)) {
            Column(modifier = Modifier.weight(1f)) {
                Input(value = consumidor.cpf?:"", onValueChange = {consumidorSetter(consumidor.copy(cpf = it))}, type = Type.CPF,label= stringResource(id = R.string.label_cpf))
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column(modifier = Modifier.weight(1f)) {
                Input(value = consumidor.celular?:"", onValueChange = {consumidorSetter(consumidor.copy(celular = it))}, type = Type.PHONE,label= stringResource(id = R.string.label_telefone))
            }
        }
        Column {
            Input(value = consumidor.nome?:"", onValueChange = {consumidorSetter(consumidor.copy(nome = it))},label= stringResource(id = R.string.label_NomeCompleto))
        }
        Row(modifier = Modifier.fillMaxWidth(1f)) {
            Column(modifier = Modifier.weight(1f)) {
                Input(value = consumidor.dataNascimento ?: "", onValueChange = { newValue ->
                    val formattedDate = formatarData(newValue)
                    consumidorSetter(consumidor.copy(dataNascimento = formattedDate))
                }, type = Type.DATE,label= stringResource(id = R.string.label_dataNascimento))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                SelectBox(value = consumidor.genero?:"",
                    onValueChange = {consumidorSetter(consumidor.copy(genero = it))},
                    expandido = expandido,
                    label = stringResource(id = R.string.label_genero),
                    generos = generos,
                    genero = generoState
                )
            }
        }
        Column {
            Input(value = consumidor.usuario?.email?:"", onValueChange = { email ->
                val usuarioAtualizado = consumidor.usuario?.copy(email = email) ?: UsuarioCriacaoDTO(email = email)
                consumidorSetter(consumidor.copy(usuario = usuarioAtualizado))
            },label= stringResource(id = R.string.label_email))
        }
        Column {
            Input(value = consumidor.usuario?.senha?:"", onValueChange = { senha ->
                val usuarioAtualizado = consumidor.usuario?.copy(senha = senha) ?: UsuarioCriacaoDTO(senha = senha)
                consumidorSetter(consumidor.copy(usuario = usuarioAtualizado))
            }, type = Type.PASSWORD, label= stringResource(id = R.string.label_senha))
        }
        Spacer(modifier = Modifier.height(2.dp))
        Button(onClick = {model.cadastrarUsuario(consumidor)}, content = {
            Title(content = stringResource(id = R.string.button_cadastrar), fontSize = 14.sp, maxLines = 1)
        })
    }
}


