package com.easy.myapplication.utils

import android.util.Log
import com.easy.myapplication.dto.Avaliacao
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

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

@OptIn(ExperimentalTime::class)
fun formatTime(time:Long): String{
    Log.e("teste",time.toString())
    if (time != null) {
        return Duration.convert(time.toDouble(),DurationUnit.MICROSECONDS,DurationUnit.HOURS)
            .toString()
            .replace(".","h:").substring(0,5)
    } else {
        return "00:00";
    }
}