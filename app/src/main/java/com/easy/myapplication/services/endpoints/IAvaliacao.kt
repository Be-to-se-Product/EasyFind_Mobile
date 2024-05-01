package com.easy.myapplication.services.endpoints

import com.easy.myapplication.dto.Avaliacao
import com.easy.myapplication.dto.AvaliacaoCadastrar
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface IAvaliacao {
    @POST("avaliacoes")
    suspend fun postAvaliacao(@Header("Authorization") token: String = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjb25zdW1pZG9yMUBlbWFpbC5jb20iLCJpYXQiOjE3MTQ1MzE2MTAsImV4cCI6MTcxODEzMTYxMH0.Fywip0UCkpHft5NOl5ZHwztzsjQaxMvcstGpM8jzhmg", @Body avaliacaoCadastrar: AvaliacaoCadastrar): Response<Avaliacao>
}