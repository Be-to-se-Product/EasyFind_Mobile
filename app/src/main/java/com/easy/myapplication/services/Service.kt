package com.easy.myapplication.services

import com.easy.myapplication.services.endpoints.ICompra
import com.easy.myapplication.BuildConfig
import com.easy.myapplication.services.endpoints.IAvaliacao
import com.easy.myapplication.services.endpoints.IConsumidor
import com.easy.myapplication.services.endpoints.IMapBox
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import com.easy.myapplication.services.endpoints.IProduto
import retrofit2.converter.gson.GsonConverterFactory



object Service {

    private const val BASEURL = BuildConfig.HOST_API

    fun ProdutoService(): IProduto {
        val cliente = Retrofit.Builder()
            .baseUrl("$BASEURL/produtos/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IProduto::class.java)
        return cliente;
    }

    fun CompraService(): ICompra {
        val cliente = Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ICompra::class.java)
        return cliente;
    }

    fun MapBoxService(): IMapBox {
        val cliente = Retrofit.Builder()
            .baseUrl("https://api.mapbox.com/directions/v5/mapbox/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IMapBox::class.java)
        return cliente;
    }

    fun AvalicaoService(): IAvaliacao{
        val avalicao = Retrofit.Builder()
            .baseUrl("$BASEURL/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IAvaliacao::class.java)
        return avalicao
    }

    fun getApiEasyFind(token: String? = null): IConsumidor {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val newRequest = if (token != null) {
                    originalRequest.newBuilder()
                        .header("Authorization", "Bearer $token")
                        .build()
                } else {
                    originalRequest
                }
                chain.proceed(newRequest)
            }
            .build()

        val cliente = Retrofit.Builder()
            .baseUrl("$BASEURL")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IConsumidor::class.java)
        return cliente
    }

}