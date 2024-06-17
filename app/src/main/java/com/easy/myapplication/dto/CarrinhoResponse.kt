package com.easy.myapplication.dto

data class CarrinhoResponse(
    val id : Long? = null,
    val quantidade : Int? = null,
    val produto : ResponseProdutoCarrinhoDTO? = null,
    val nomeEmpresa : String? = null,
    val idEmpresa : Long? = null,
)
