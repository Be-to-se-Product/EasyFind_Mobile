package com.easy.myapplication.screens.Login

import android.util.Log
import android.content.Context
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.easy.myapplication.services.Service
import com.easy.myapplication.dto.ConsumidorCriacaoDTO
import com.easy.myapplication.dto.UsuarioCriacaoDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

val Context.tokenUsuario: DataStore<Preferences> by preferencesDataStore("token")
class Model(private val context: Context):ViewModel(){
    val filmes = MutableLiveData(SnapshotStateList<UsuarioCriacaoDTO>())
    val erroApi = MutableLiveData("")

    fun loginUsuario(usuario: UsuarioCriacaoDTO){
        val api = Service.getApiEasyFind();

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val post  = api.loginConsumidor(usuario)
                if (post.isSuccessful){
                    post.body()?.token?.let { saveToken(it) }
                    erroApi.postValue("")
                    Log.d("api",post.body().toString())
                }else{
                    erroApi.postValue(post.errorBody()!!.string())
                }
            } catch (e:Exception){
                Log.e("api","Deu ruim no post! ${e.message}, ${usuario}")
                erroApi.postValue(e.message)
            }
        }
    }

    suspend fun getToken(): String? {
        val token = stringPreferencesKey("token")
        val preferences = context.tokenUsuario.data.first()
        return preferences[token]
    }

    fun cadastrarUsuario(consumidor: ConsumidorCriacaoDTO){
        CoroutineScope(Dispatchers.IO).launch {
            val api = Service.getApiEasyFind(getToken());
            try{
                val post  = api.cadastrarConsumidor(consumidor)
                if (post.isSuccessful){
                    Log.d("api ${consumidor}",post.body().toString())
                }else{
                    erroApi.postValue(post.errorBody()!!.string())
                    Log.d("api ${consumidor} ",post.body().toString())
                }
            }catch (e:Exception){
                Log.e("api","Deu ruim no post! ${e.message} ${consumidor}")
                erroApi.postValue(e.message)
            }
        }
    }

    private suspend fun saveToken(token: String) {
        context.tokenUsuario.edit { settings ->
            settings[stringPreferencesKey("token")] = token
        }
    }

}

