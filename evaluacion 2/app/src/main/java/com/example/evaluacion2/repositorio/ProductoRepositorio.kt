package com.example.evaluacion2.repositorio

import com.example.evaluacion2.Data.Modelo.Producto
import com.example.evaluacion2.Data.network.ProductoService
import retrofit2.Response

class ProductoRepositorio(
    private val service: ProductoService = ApiNet.productoService
) {
    private fun <T> Response<T>.unwrap(): T {
        if (isSuccessful) {
            val body = body()
            if (body != null) return body
            // Usa code() y message() como métodos, y reemplaza -> correcto
            throw Exception(
                when (code()) {
                    204, 205 -> "OK sin contenido (HTTP ${code()})"
                    else     -> "Respuesta vacía del servidor (HTTP ${code()})"
                }
            )
        } else {
            val msg = try { errorBody()?.string() } catch (_: Exception) { null }
            // IMPORTANTE: code() y message() (no $code ni $message)
            throw Exception("HTTP ${code()}: ${msg ?: message() ?: "Error desconocido"}")
        }
    }

    suspend fun listar(): Result<List<Producto>> =
        runCatching { service.listar().unwrap() }

    suspend fun obtener(id: Int): Result<Producto> =
        runCatching { service.obtener(id).unwrap() }

    suspend fun crear(nuevo: Producto): Result<Producto> =
        runCatching { service.crear(nuevo).unwrap() }

    suspend fun actualizar(id: Int, datos: Producto): Result<Producto> =
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