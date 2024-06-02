package com.easy.myapplication.dto

data class ResponseItemVendaDTO(
    val id: Long? = null,
    val quantidade: Int? = null,
    val produto: ProdutoDetalhamentoDTO? = null,
)
