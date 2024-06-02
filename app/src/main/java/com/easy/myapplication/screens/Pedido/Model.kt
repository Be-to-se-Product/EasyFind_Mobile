package com.easy.myapplication.screens.Pedido

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.easy.myapplication.dto.ResponsePedido
import com.easy.myapplication.services.Service
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Model : ViewModel()  {
    private val pedidoService = Service.PedidosService();
    val pedidos = MutableLiveData(SnapshotStateList<ResponsePedido>())
    val erroApi = MutableLiveData("")
    fun getPedidos(status:String?=null) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = pedidoService.getPedidos(status = status);
                if (response.isSuccessful) {
                    pedidos.value!!.clear()
                    Log.e("Pedidos",response.body()!!.size.toString())
                    pedidos.value!!.addAll(response.body() ?: listOf() )
                } else {
                    erroApi.postValue(response.errorBody()?.string() ?: "")
                }
            } catch (e: Exception) {
                Log.e("Error sexo",e.message.toString())
                erroApi.postValue(e.message)
            }

        }

    }
}