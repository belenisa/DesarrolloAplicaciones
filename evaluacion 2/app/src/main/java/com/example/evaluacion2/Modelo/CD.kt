package com.example.evaluacion2.Modelo

import androidx.annotation.DrawableRes

data class CD(
    val autor: String,
    val anio: Int,
    val titulo: String,
    val genero: String,
    val precio: Int,
    @DrawableRes val imageResId: Int
)

