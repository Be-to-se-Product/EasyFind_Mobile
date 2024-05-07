package com.easy.myapplication.services.endpoints

import com.easy.myapplication.dto.ConsumidorCriacaoDTO
import com.easy.myapplication.dto.ResponseConsumidorDTO
import com.easy.myapplication.dto.UsuarioCriacaoDTO
import com.easy.myapplication.dto.UsuarioTokenDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IConsumidor {
    @POST("/api/consumidores")
    suspend fun cadastrarConsumidor(@Body novoConsumidor: ConsumidorCriacaoDTO): Response<ResponseConsumidorDTO>

    @POST("/api/usuarios/login")
    suspend fun loginConsumidor(@Body consumidor: UsuarioCriacaoDTO): Response<UsuarioTokenDTO>

}