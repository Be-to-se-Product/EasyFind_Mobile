package com.easy.myapplication.screens.Produto

import LatandLong
import android.util.Log
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.easy.myapplication.dto.AvaliacaoCadastrar
import com.easy.myapplication.dto.Produto
import com.easy.myapplication.services.Service
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


data class ProdutoPedido(
    val id:Long?=null,
    val quantidade:Int?=null,
    val idEstabelecimento:Long?=null,
    val preco: Double?=null,
    val origin: String?=null,
):java.io.Serializable


class ProdutoViewModel : ViewModel() {
    val produto = MutableLiveData(Produto())
    val erroApi = MutableLiveData("")
    val latLong = MutableLiveData(LatandLong())
    val produtoVenda = MutableLiveData(ProdutoPedido());
    private val produtoService = Service.ProdutoService()
    private val avalicaoService = Service.AvalicaoService()

    fun getProdutoById(id : Long,latitude:Double,longitude:Double){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = produtoService.getProdutoId(id,latitude,longitude)
                if(response.isSuccessful){

                    val produts = response.body()
                    produto.postValue(produts)
                    produtoVenda.postValue(produtoVenda.value?.copy(produts?.id!!,1,produts.estabelecimento?.id!!,produts.precoAtual!!))
                } else{
                    erroApi.postValue(response.errorBody()?.string() ?: "")
                }
            }catch (e: Exception){
                erroApi.postValue(e.message)
            }
        }
    }

    fun cadastroAvalicao(avaliacaoCadastrar: AvaliacaoCadastrar){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = avalicaoService.postAvaliacao(avaliacaoCadastrar = avaliacaoCadastrar)
                if (response.isSuccessful) {
                    latLong.value?.latitude?.let {
                        getProdutoById(
                            avaliacaoCadastrar.produto,
                            it, latLong.value!!.longitude)
                    }
                } else {
                    erroApi.postValue(response.errorBody()?.string() ?: "")
                }
            }catch (e: Exception){
                Log.e("Erro", e.message.toString())
                erroApi.postValue(e.message)
            }
        }
    }
}