package com.example.evaluacion2.Data.Modelo

data class Direccion(
    val id: Int? = null,
    val direccion: String,
    val codigoPostal: Int,
    val comuna: Comuna? = null
)