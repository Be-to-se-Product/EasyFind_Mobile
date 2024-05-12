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
    suspend fun postAvaliacao(@Body avaliacaoCadastrar: AvaliacaoCadastrar): Response<Avaliacao>
}