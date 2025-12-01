
package com.example.evaluacion2.repositorio

import com.example.evaluacion2.Data.Modelo.EstadoVenta
import com.example.evaluacion2.Data.network.Rol.EstadoVentaService
import retrofit2.Response

class EstadoVentaRepositorio(
    private val service: EstadoVentaService = ApiNet.estadoVentaService
) {
    // Convierte Response<T> a éxito o lanza excepción con mensaje útil
    private fun <T> Response<T>.unwrap(): T {
        if (isSuccessful) {
            val body = body()
            if (body != null) return body
            throw Exception("Respuesta vacía del servidor (HTTP ${code()})")
        } else {
            val msg = try { errorBody()?.string() } catch (_: Exception) { null }
            throw Exception("HTTP ${code()}: ${msg ?: message() ?: "Error desconocido"}")
        }
    }

    suspend fun listar(): Result<List<EstadoVenta>> =
        runCatching { service.listar().unwrap() }

    suspend fun obtener(id: Int): Result<EstadoVenta> =
        runCatching { service.obtener(id).unwrap() }

    suspend fun actualizar(id: Int, datos: EstadoVenta): Result<EstadoVenta> =
        runCatching { service.actualizar(id, datos).unwrap() }
}
