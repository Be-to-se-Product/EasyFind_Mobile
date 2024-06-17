package com.easy.myapplication.services.endpoints

import com.easy.myapplication.dto.ResponsePedido
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IPedido {
    @GET("consumidor")
    suspend fun getPedidos(@Query("status") status:String?): Response<List<ResponsePedido>>
}