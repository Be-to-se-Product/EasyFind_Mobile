package com.easy.myapplication.dto

import java.io.Serializable

data class ResponsePedido(
    val id : Long? = null,
    val dataPedido : String? = null,
    val statusPedido : String? = null,
    val isPagamentoOnline : Boolean? = null,
    val metodoPagamento : String? = null,
    val itens : List<ResponseItemVendaDTO>? = null,
    val estabelecimento :EstabelecimentoResponsePedido? = null
):Serializable
