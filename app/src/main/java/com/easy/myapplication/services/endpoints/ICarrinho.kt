package com.easy.myapplication.services.endpoints

import com.easy.myapplication.dto.CarrinhoRequestDTO
import com.easy.myapplication.dto.CarrinhoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ICarrinho {
    @GET("carrinhos")
    suspend fun getCarrinho(@Query("id") id:Long): Response<List<CarrinhoResponse>>

    @PATCH("carrinhos/{id}")
    suspend fun editCarrinho(@Path("id") id:Long, @Query("quantidade") quantidade:Int): Response<Void>

    @POST("carrinhos")
    suspend fun postCarrinho(@Body carrinho: CarrinhoRequestDTO): Response<CarrinhoResponse>
}