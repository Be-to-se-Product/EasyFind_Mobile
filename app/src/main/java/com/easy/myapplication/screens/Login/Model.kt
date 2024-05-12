package com.easy.myapplication.screens.Login

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.easy.myapplication.services.Service
import com.easy.myapplication.dto.ConsumidorCriacaoDTO
import com.easy.myapplication.dto.UsuarioCriacaoDTO
import com.easy.myapplication.repositories.StorageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class Model(private val storage: StorageRepository):ViewModel(

){
    val usuario = MutableLiveData(UsuarioCriacaoDTO())
    val erroApi = MutableLiveData("")
    val api = Service.AutheticationService()

    fun loginUsuario(usuario: UsuarioCriacaoDTO, navigate: () -> Unit){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val post  = api.loginConsumidor(usuario)
                if (post.isSuccessful){

                    val body = post.body()
                    body?.nome.let {
                        if (it != null) {
                            storage.saveToDataStore("nome",it)
                        }
                    }

                    body?.token?.let {
                        Log.d("Login", "Login bem-sucedido, token recebido: $it")
                        storage.saveToDataStore("token",it)
                        MainScope().launch {
                            navigate()
                        }
                    }



                } else {
                    erroApi.postValue(post.errorBody()?.string() ?: "Erro desconhecido")
                    Log.e("Login", "Erro na solicitação de login: ${post.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                erroApi.postValue(e.message)
                Log.e("Login", "Exceção durante o login: ${e.message}")
            }
        }
    }

    fun cadastrarUsuario(consumidor: ConsumidorCriacaoDTO){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val post  = api.cadastrarConsumidor(consumidor)
                if (post.isSuccessful){
                    Log.d("api $consumidor",post.body().toString())
                }else{
                    erroApi.postValue(post.errorBody()!!.string())
                    Log.d("api $consumidor ",post.body().toString())
                }
            }catch (e:Exception){
                erroApi.postValue(e.message)
            }
        }
    }

}