package com.easy.myapplication.dto


enum class Metodo{
    CARTAO_CREDITO,CARTAO_DEBITO, DINHEIRO,PIX
}
data class FilterDTO(
    val nome:String?=null,
    var distancia:Float? = null,
    val metodoPagamento:String? = null
)