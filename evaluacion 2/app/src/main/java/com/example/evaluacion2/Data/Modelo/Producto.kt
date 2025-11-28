package com.example.evaluacion2.Data.Modelo

data class Producto(
    val id: Int? = null,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val stock: Int,
    val tipoProducto: TipoProducto? = null,
    val artistas: Artistas? = null
)