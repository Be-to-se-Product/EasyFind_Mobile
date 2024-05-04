package com.easy.myapplication.dto

data class RoutesMapper(
    val name: String?,
    val duration: Double,
    val distance: Double,
    val instruction: String,
    val modifier: String?
)