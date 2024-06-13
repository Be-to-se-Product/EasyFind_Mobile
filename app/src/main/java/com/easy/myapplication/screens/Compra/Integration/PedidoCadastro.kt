package com.easy.myapplication.screens.Compra.Integration

class PedidoCadastro {
    var idConsumidor : Long? = null
    var idEstabelecimento: Long? = null
    var itens: List<ItemVenda> = mutableListOf()
    var metodo: MetodoPagamento? = null
    var origem: String? = null
}