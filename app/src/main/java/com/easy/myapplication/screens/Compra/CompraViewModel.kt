package com.easy.myapplication.screens.Compra

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy.myapplication.dto.MetodoPagamento
import com.easy.myapplication.dto.Produto
import com.easy.myapplication.screens.Compra.Integration.ItemVenda
import com.easy.myapplication.screens.Compra.Integration.MetodoPagamentoAceito
import com.easy.myapplication.screens.Compra.Integration.PedidoCadastro
import com.easy.myapplication.services.Service
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompraViewModel() : ViewModel() {
    val metodosPagamento = MutableLiveData(SnapshotStateList<MetodoPagamentoAceito>())
    private val erroApi = MutableLiveData("")
    private val compraService = Service.CompraService()

    fun getMetodos(id: Int = 1) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = compraService.getMetodosPagamento(id);
                Log.e("Executou", "salve pessoal ${response.isSuccessful}")
                if (response.isSuccessful) {
                    print("Chegou pessoal daora")
                    metodosPagamento.value!!.clear()
                    metodosPagamento.value!!.addAll(
                        response.body() ?: listOf(MetodoPagamentoAceito())
                    )
                } else {
                    erroApi.postValue(response.errorBody()?.string() ?: "")
                }
            } catch (e: Exception) {
                Log.e("Executou", "Executou ${e.message}")
                print(e.message)
                erroApi.postValue(e.message)
            }

        }

    }

    fun postPedido(pedidoCadastro: PedidoCadastro) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = compraService.postPedido(pedidoCadastro)
                if (response.isSuccessful) {
                    print("Pedido cadastrado com sucesso")
                } else {
                    erroApi.postValue(response.errorBody()?.string() ?: "")
                }
            } catch (e: Exception) {
                erroApi.postValue(e.message)
            }
        }
    }
}