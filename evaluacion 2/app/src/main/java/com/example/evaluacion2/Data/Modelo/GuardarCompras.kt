package com.example.evaluacion2.Modelo

import androidx.compose.runtime.mutableStateListOf


class GuardarCompras {
    private val compras = mutableStateListOf<CD>()
    val carrito: List<CD> get() = compras

    fun agregar(cd: CD) {
        compras.add(cd)
    }
}