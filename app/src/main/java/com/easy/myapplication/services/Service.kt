package com.easy.myapplication.services
import com.easy.myapplication.BuildConfig
import com.easy.myapplication.services.endpoints.IAvaliacao
import com.easy.myapplication.services.endpoints.IMapBox
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

}


