package com.easy.myapplication.utils

import com.easy.myapplication.dto.Avaliacao

fun mediaAvaliacao(avaliacoes: List<Avaliacao>?): Double {

    if (avaliacoes.isNullOrEmpty()) {
        return 0.0
    }
    val quantidadesEstrelas = avaliacoes.map { it.qtdEstrela }

    // Usando reduce para calcular a soma das quantidades de estrelas
    val soma = quantidadesEstrelas.reduce { acc, qtdEstrela ->
        if (qtdEstrela != null) acc?.plus(
            qtdEstrela
        ) else acc
    }


    val media = soma?.div(avaliacoes.size.toDouble())

    return media ?:0.0;
}