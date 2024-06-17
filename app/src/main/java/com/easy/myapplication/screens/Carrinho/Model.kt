package com.easy.myapplication.screens.Carrinho

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.easy.myapplication.dto.CarrinhoRequestDTO
import com.easy.myapplication.dto.CarrinhoResponse
import com.easy.myapplication.services.Service
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Model : ViewModel() {
    private val carrinhoService = Service.CarrinhoService();
    val carrinhos = MutableLiveData(SnapshotStateList<CarrinhoResponse>())
    val erroApi = MutableLiveData("")

    fun getCarrinhos() {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = carrinhoService.getCarrinho(id = 1);
                if (response.isSuccessful) {
                    carrinhos.value!!.clear()
                    carrinhos.value!!.addAll(response.body() ?: listOf() )
                } else {
                    erroApi.postValue(response.errorBody()?.string() ?: "")
                }
            } catch (e: Exception) {
                erroApi.postValue(e.message)
            }
        }
    }

    fun editCarrinho(id: Long, quantidade: Int) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = carrinhoService.editCarrinho(id,quantidade);
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        getCarrinhos()
                    }
                } else {
                    erroApi.postValue(response.errorBody()?.string() ?: "")
                }
            } catch (e: Exception) {
                erroApi.postValue(e.message)
            }

        }

    }

    fun postCarrinho(carrinho: CarrinhoRequestDTO){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = carrinhoService.postCarrinho(carrinho);
                if (response.isSuccessful) {
                } else {
                    erroApi.postValue(response.errorBody()?.string() ?: "")
                }
            } catch (e: Exception) {
                erroApi.postValue(e.message)
            }
        }
    }
}