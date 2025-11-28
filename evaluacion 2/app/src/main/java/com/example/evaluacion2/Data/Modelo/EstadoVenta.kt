package com.example.evaluacion2.Data.Modelo


enum class estados {
    Cancelado, Entregado, Pendiente
}

data class EstadoVenta (
    val id: Int? = null,
    val estado: estados
)