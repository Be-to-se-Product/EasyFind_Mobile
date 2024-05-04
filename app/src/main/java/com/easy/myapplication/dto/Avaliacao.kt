    package com.easy.myapplication.dto

    import com.google.gson.annotations.SerializedName
    import java.time.LocalDate


    data class  Avaliacao (
        val usuario: String? = null,
        val qtdEstrela: Int? = null,
        val data: String? = null,
        val descricao: String? = null
    )