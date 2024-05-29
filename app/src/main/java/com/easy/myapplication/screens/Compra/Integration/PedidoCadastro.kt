package com.easy.myapplication.screens.Compra.Integration

import com.easy.myapplication.screens.Compra.Integration.ItemVenda
import com.easy.myapplication.screens.Compra.Integration.MetodoPagamento

class PedidoCadastro {
    var idEstabelecimento: Long? = null
    var itens: List<ItemVenda> = mutableListOf()
    var metodo: MetodoPagamento? = null
    var origem: String? = null
}