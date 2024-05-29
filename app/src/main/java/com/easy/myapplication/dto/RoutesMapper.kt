package com.easy.myapplication.dto

import androidx.compose.runtime.snapshots.SnapshotStateList

// inicializar a snapshot list

data class TargetRoutes(
    val distance: Double=0.0,
    val duration: Double =0.0,
    val routes: SnapshotStateList<RoutesMapper> = SnapshotStateList()
)
data class RoutesMapper(
    val name: String?,
    val duration: Double,
    val distance: Double,
    val instruction: String,
    val modifier: String?
)