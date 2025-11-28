package com.example.evaluacion2.Data.Modelo

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf


class GuardarCompras {
    var carrito = mutableStateListOf<CD>()
    var cantidades = mutableStateMapOf<String, Int>()

    fun agregar(cd: CD) {
        if (!carrito.contains(cd)) {
            carrito.add(cd)
        }
        cantidades[cd.titulo] = (cantidades[cd.titulo] ?: 0) + 1
    }
}