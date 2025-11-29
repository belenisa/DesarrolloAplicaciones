package com.example.evaluacion2.repositorio

import com.example.evaluacion2.Data.Modelo.EstadoVenta
import com.example.evaluacion2.Data.network.Rol.EstadoVentaService


class EstadoVentaRepositorio (
    private val service: EstadoVentaService = ApiNet.estadoVentaService
) {
    suspend fun listarRoles(): List<EstadoVenta>? {
        val response = service.listar()
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun obtenerRol(id: Int): EstadoVenta? {
        val response = service.obtener(id)
        return if (response.isSuccessful) response.body() else null
    }
}