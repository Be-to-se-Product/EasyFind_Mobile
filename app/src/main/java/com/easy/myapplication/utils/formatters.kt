package com.easy.myapplication.utils

import android.annotation.SuppressLint
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
    if (time != null) {
        return Duration.convert(time.toDouble(),DurationUnit.SECONDS,DurationUnit.HOURS)
            .toString()
            .replace(".","h:").substring(0,5)
    } else {
        return "00:00";
    }
}


fun conversorDistancia(metros: Double): String {
    val medidas = listOf("m", "km");
    var contadora = 0;
    var distanciaConvertida = metros ;
    while(distanciaConvertida > 1000 && contadora < medidas.size-1){
        distanciaConvertida /= 1000;
        contadora++;
    }
    return "$distanciaConvertida ${medidas[contadora]}";
}

@SuppressLint("DefaultLocale")
fun conversorTime(segundos: Double): String {
    val horas = segundos / 3600
    val minutos = (segundos % 3600) / 60
    return if (horas > 0) {
        String.format("%.0fh %.0f min", horas, minutos)
    } else {
       String.format("%.0fmin", minutos)
    }
}