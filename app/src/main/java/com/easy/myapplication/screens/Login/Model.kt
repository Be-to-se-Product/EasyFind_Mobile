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
import kotlinx.coroutines.launch

class Model(private val storage: StorageRepository):ViewModel(

){
    val filmes = MutableLiveData(SnapshotStateList<UsuarioCriacaoDTO>())
    val erroApi = MutableLiveData("")

    fun loginUsuario(usuario: UsuarioCriacaoDTO, navigate: () -> Unit){
        val api = Service.AutheticationService()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val post  = api.loginConsumidor(usuario)
                if (post.isSuccessful){
                    post.body()?.token?.let {
                        storage.saveToDataStore("token",it)
                        navigate()
                    }
                    erroApi.postValue("")
                    Log.d("api",post.body().toString())
                }else{
                    erroApi.postValue(post.errorBody()!!.string())
                }
            } catch (e:Exception){
                erroApi.postValue(e.message)
            }
        }
    }

    fun cadastrarUsuario(consumidor: ConsumidorCriacaoDTO){
        CoroutineScope(Dispatchers.IO).launch {
            val api = Service.AutheticationService();
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