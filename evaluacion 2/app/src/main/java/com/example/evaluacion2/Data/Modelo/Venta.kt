package com.example.evaluacion2.Modelo

import com.example.evaluacion2.Data.Modelo.EstadoVenta
import com.example.evaluacion2.Data.Modelo.MetodoEnvio
import com.example.evaluacion2.Data.Modelo.MetodoPago
import com.example.evaluacion2.Data.Modelo.Producto


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