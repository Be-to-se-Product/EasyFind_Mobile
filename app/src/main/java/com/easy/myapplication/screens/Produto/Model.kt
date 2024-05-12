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
    val preco: Double?=null
):java.io.Serializable


class ProdutoViewModel : ViewModel() {
    val produto = MutableLiveData(Produto())
    val erroApi = MutableLiveData("")
    val avaliacao = MutableLiveData(AvaliacaoCadastrar())
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

    fun cadastroAvalicao(id: Long){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                avaliacao.postValue(avaliacao.value?.copy(produto = id))
                if(avaliacao.value != null) {
                    val response = avalicaoService.postAvaliacao(avaliacaoCadastrar = avaliacao.value!!)
                    if (response.isSuccessful) {
                        latLong.value?.latitude?.let {
                            getProdutoById(id,
                                it, latLong?.value!!.longitude)
                        }
                    } else {
                        erroApi.postValue(response.errorBody()?.string() ?: "")
                    }
                }
            }catch (e: Exception){
                erroApi.postValue(e.message)
            }
        }
    }
}