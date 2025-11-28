package com.example.evaluacion2.repositorio

import com.example.evaluacion2.Data.Modelo.Rol
import com.example.evaluacion2.Data.network.Rol.RolService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


class VentaRepositorio(
    private val service: VentaService
) {
    suspend fun listarVentas(): List<Venta>? {
        val response = service.listar()
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun obtenerVenta(id: Int): Venta? {
        val response = service.obtener(id)
        return if (response.isSuccessful) response.body() else null
    }
}
