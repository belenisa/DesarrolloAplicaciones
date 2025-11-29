package com.example.evaluacion2.repositorio

import com.example.evaluacion2.Data.Modelo.Direccion
import com.example.evaluacion2.Data.Modelo.Imagen
import com.example.evaluacion2.Data.network.DireccionService
import retrofit2.Response


class DireccionRepositorio(
    private val service: DireccionService = ApiNet.direccionService
) {
    private fun <T> Response<T>.unwrap(): T {
        if (isSuccessful) {
            val body = body()
            if (body != null) return body
            throw Exception(
                when (code()) {
                    204, 205 -> "OK sin contenido (HTTP ${code()})"
                    else     -> "Respuesta vacía del servidor (HTTP ${code()})"
                }
            )
        } else {
            val msg = try { errorBody()?.string() } catch (_: Exception) { null }
            throw Exception("HTTP ${code()}: ${msg ?: message() ?: "Error desconocido"}")
        }
    }

    suspend fun listar(): Result<List<Direccion>> =
        runCatching { service.listar().unwrap() }

    suspend fun obtener(id: Int): Result<Direccion> =
        runCatching { service.obtener(id).unwrap() }

    suspend fun crear(nuevo: Direccion): Result<Direccion> =
        runCatching { service.crear(nuevo).unwrap() }

    suspend fun actualizar(id: Int, datos: Direccion): Result<Direccion> =
        runCatching { service.actualizar(id, datos).unwrap() }

    suspend fun eliminar(id: Int): Result<Unit> = runCatching {
        val resp = service.eliminar(id)
        if (resp.isSuccessful) {
            Unit
        } else {
            val msg = try { resp.errorBody()?.string() } catch (_: Exception) { null }
            throw Exception("HTTP ${resp.code()}: ${msg ?: resp.message() ?: "Error desconocido"}")
        }
    }
}
