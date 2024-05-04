package com.easy.myapplication.services.endpoints
import com.easy.myapplication.dto.Produto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
interface IProduto {
    @GET("mapa")
    suspend fun getMapaProdutos(@Query("latitude") latitude:Double?, @Query("longitude") longitude:Double?, @Query("distancia")  distancia:Float?=50.0F, @Query("nome") nome:String?=null,@Query("metodoPagamento") metodoPagamento:String?=null): Response<List<Produto>>

    @GET("mobile/{id}")
    suspend fun getProdutoId(@Path("id") id:Long,@Query("latitude") latitude:Double, @Query("longitude") longitude:Double): Response<Produto>

}