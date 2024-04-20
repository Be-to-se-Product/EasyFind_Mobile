package com.easy.myapplication.services

import retrofit2.Retrofit
import com.easy.myapplication.services.endpoints.IProduto
import retrofit2.converter.gson.GsonConverterFactory

object Service {
    const val BASE_URL = "http://192.168.18.179:8080/api/produtos/";
    fun ProdutoService(): IProduto {
        val cliente = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IProduto::class.java)
        return cliente;
    }

}