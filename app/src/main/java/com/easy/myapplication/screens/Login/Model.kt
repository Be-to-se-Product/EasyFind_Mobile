package com.easy.myapplication.screens.Login

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.easy.myapplication.services.Service
import com.easy.myapplication.dto.ConsumidorCriacaoDTO
import com.easy.myapplication.dto.UsuarioCriacaoDTO
import com.easy.myapplication.repositories.StorageRepository
import com.easy.myapplication.text.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Objects
import java.util.logging.Handler


class Model(private val storage: StorageRepository):ViewModel(){
    val erroApi = MutableLiveData("")
    val message = MutableLiveData(Message());
    val loading = MutableLiveData(false);
    val ConsumidorService = Service.AutheticationService();
    val CadastroService = Service.CadastrarService();



     fun verificarUsuarioLogado(navigate: () -> Unit){
        CoroutineScope(Dispatchers.IO).launch{
            try {
                val token = storage.readFromDataStore("token")
                if(Objects.nonNull(token)){
                    MainScope().launch{
                        navigate()
                    }
                }
            }
            catch (e :Exception){
                    Log.e("Erro",e.message.toString())
            }

        }
    }

    fun loginUsuario(usuario: UsuarioCriacaoDTO, navigate: () -> Unit){
        loading.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val post  = ConsumidorService.loginConsumidor(usuario)
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
                    if(post.code()==401 || post.code()==403){
                        message.postValue(message.value?.copy(show = true,message="Email e/ou senha inválidos"))

                    }
                    else{

                        message.postValue(message.value?.copy(show = true,message="Houve um erro de conexão"))
                    }

                    erroApi.postValue(post.errorBody()?.string() ?: "Erro desconhecido")
                    Log.e("Login", "Erro na solicitação de login: ${post.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                erroApi.postValue(e.message)
                Log.e("Login", "Exceção durante o login: ${e.message}")

            }
            finally {
                loading.postValue(false)
                delay(2000)
                message.postValue(Message(show = false))
            }

        }
    }

    fun cadastrarUsuario(consumidor: ConsumidorCriacaoDTO){
        loading.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val post  = CadastroService.cadastrarConsumidor(consumidor)
                if (post.isSuccessful){
                    Log.d("api $consumidor",post.body().toString())
                }else{
                    if(post.code()==409){
                       message.postValue(message.value?.copy(
                           show = true,
                           message="Já há um usuário cadastrado na plataforma"
                       ))
                    }
                }
            }catch (e:Exception){
                Log.e("Erro",e.message.toString())
                erroApi.postValue(e.message)
            }
            finally {
                loading.postValue(false)
                delay(2000)
                message.postValue(message.value?.copy(show=false))
            }
        }
    }

}