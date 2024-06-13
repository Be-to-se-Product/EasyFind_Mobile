package com.easy.myapplication.dto

data class ResponseProdutoCarrinhoDTO(
    val id : Long? = null,
    val nome : String? = null,
    val preco : Double? = null,
    val imagens : List<String>? = null,
)
