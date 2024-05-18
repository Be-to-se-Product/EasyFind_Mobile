package com.easy.myapplication.services

import android.util.Log
import com.easy.myapplication.services.endpoints.ICompra
import com.easy.myapplication.BuildConfig
import com.easy.myapplication.repositories.StorageRepository
import com.easy.myapplication.services.endpoints.IAvaliacao
import com.easy.myapplication.services.endpoints.IConsumidor
import com.easy.myapplication.services.endpoints.IMapBox
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import com.easy.myapplication.services.endpoints.IProduto
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.converter.gson.GsonConverterFactory



object Service: KoinComponent {

    private val BASEURL = BuildConfig.HOST_API
    val gson =GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
    val storage : StorageRepository by inject<StorageRepository>();
    var navigate = {};

    fun ProdutoService(): IProduto {
        val cliente = Instance("produtos")
            .create(IProduto::class.java)
        return cliente;
    }

    fun CompraService(): ICompra {
        val cliente = Instance("")
            .create(ICompra::class.java)
        return cliente;
    }


    fun CadastrarService() :IConsumidor{
        val cliente = Instance("")
            .create(IConsumidor::class.java)
        return cliente
    }
    fun MapBoxService(): IMapBox {
        val cliente = Retrofit.Builder()
            .baseUrl("https://api.mapbox.com/directions/v5/mapbox/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(IMapBox::class.java)
        return cliente;
    }

    fun AvalicaoService(): IAvaliacao{

        val avalicao = Instance("")
            .create(IAvaliacao::class.java)
        return avalicao
    }

    fun AutheticationService(): IConsumidor {
        val cliente = Instance("usuarios").create(IConsumidor::class.java);
        return cliente;
    }

    fun Instance(endpoint:String): Retrofit {
        val client = Retrofit.Builder().baseUrl("$BASEURL/${if (endpoint.isEmpty()) "" else "$endpoint/"}").client(Interceptor()).addConverterFactory(GsonConverterFactory.create(gson)).build()
        return client;
    }
     fun Interceptor(): OkHttpClient {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val token = runBlocking { storage.readFromDataStore("token") }
                var newRequest = originalRequest.newBuilder()
                if (token!=null) {
                newRequest.header("Authorization", "Bearer $token")
                }
                chain.proceed(newRequest.build())

            }

        okHttpClient.addInterceptor{
            chain->
            val request = chain.request();
            val response = chain.proceed(request)
            if(response.code() == 401 || response.code()==403){
                navigate()
            }
            response
        }

         return okHttpClient.build()

    }
}