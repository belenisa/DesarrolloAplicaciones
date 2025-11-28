package com.example.evaluacion2.Modelo



data class Venta (
    val id: Int? = null,
    val cantida: Int,
    val precioTotal: Double,
    val producto: Producto? = null, // solo para enviar en POST/PUT
    val estadoVenta: EstadoVenta? = null,
    val metodoPago: MetodoPago? = null,
    val metodoEnvio: MetodoEnvio? = null,
    val usuario: Usuarios? = null
)