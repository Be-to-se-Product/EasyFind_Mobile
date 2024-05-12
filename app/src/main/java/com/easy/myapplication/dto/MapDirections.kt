package com.easy.myapplication.dto

data class Instruction(
    val instruction: String,
    val modifier: String
)


data class Steps(
    val name: String?,
    val duration: Double,
    val distance: Double,
    val maneuver :Instruction
)

data class Rota(
    val steps: List<Steps>,

)

data class Legs(
    val distance: Double,
    val duration: Double,
    val legs: List<Rota>
)

data class MapDirections(
    val routes: List<Legs>
)