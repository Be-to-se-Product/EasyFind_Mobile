package com.easy.myapplication.text

import androidx.compose.ui.graphics.Color


enum class TypesMessage {
    ERROR,WARN,SUCESS
}
data class Message(
    val show:Boolean=false,
    val message: String="",
    val type:TypesMessage?=null
)