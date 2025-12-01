
package com.example.evaluacion2.Modelo

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import com.example.evaluacion2.Data.Modelo.Producto

class GuardarCompras {
    var carrito = mutableStateListOf<Producto>()
    var cantidades = mutableStateMapOf<String, Int>()

    fun agregarProducto(producto: Producto) {
        if (!carrito.contains(producto)) {
            carrito.add(producto)
            cantidades[producto.nombre] = 1
        } else {
            incrementarCantidad(producto)
        }
    }

    fun quitarProducto(producto: Producto) {
        carrito.remove(producto)
        cantidades.remove(producto.nombre)
    }

    fun incrementarCantidad(producto: Producto) {
        val actual = cantidades[producto.nombre] ?: 1
        cantidades[producto.nombre] = actual + 1
    }

    fun decrementarCantidad(producto: Producto) {
        val actual = cantidades[producto.nombre] ?: 1
        if (actual > 1) {
            cantidades[producto.nombre] = actual - 1
        } else {
            quitarProducto(producto)
        }
    }

    fun calcularTotal(): Double {
        return carrito.sumOf { producto ->
            val cantidad = cantidades[producto.nombre] ?: 1
            producto.precio * cantidad
        }
    }

    fun generarVenta(usuario: Usuarios): Venta {
        val total = calcularTotal()
        return Venta(
            cantida = carrito.sumOf { cantidades[it.nombre] ?: 1 },
            precioTotal = total,
            producto = null,
            estadoVenta = null,
            metodoPago = null,
            metodoEnvio = null,
            usuario = usuario
        )
    }
}
