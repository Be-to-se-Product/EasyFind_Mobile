package com.easy.myapplication.services.endpoints

import com.easy.myapplication.screens.Compra.Integration.MetodoPagamentoAceito
import com.easy.myapplication.screens.Compra.Integration.PedidoCadastro
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ICompra {
    @GET("estabelecimentos/metodos/{id}")
    suspend fun getMetodosPagamento(@Path(value = "id") id: Long): Response<List<MetodoPagamentoAceito>>

    @POST("pedidos")
    suspend fun postPedido(@Body pedido: PedidoCadastro): Response<Void>
}