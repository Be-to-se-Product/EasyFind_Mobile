package com.easy.myapplication.services

import com.easy.myapplication.services.endpoints.ICompra
import com.easy.myapplication.services.endpoints.IMapBox
import retrofit2.Retrofit
import com.easy.myapplication.services.endpoints.IProduto
import retrofit2.converter.gson.GsonConverterFactory

object Service {
    const val BASE_URL = "http://192.168.23.57:8080/api/";
    fun ProdutoService(): IProduto {
        val cliente = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IProduto::class.java)
        return cliente;
    }

    fun CompraService(): ICompra {
        val cliente = Retrofit.Builder()
            .baseUrl(Service.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ICompra::class.java)
        return cliente;
    }

}

fun MapBoxService(): IMapBox {
    val cliente = Retrofit.Builder()
        .baseUrl("https://api.mapbox.com/directions/v5/mapbox/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(IMapBox::class.java)
    return cliente;
}