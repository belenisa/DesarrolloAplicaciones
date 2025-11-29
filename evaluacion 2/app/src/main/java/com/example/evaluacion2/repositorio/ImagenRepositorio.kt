
package com.example.evaluacion2.repositorio

import com.example.evaluacion2.Data.Modelo.Imagen
import com.example.evaluacion2.Data.network.ImagenService
import retrofit2.Response

class ImagenRepositorio(
    private val service: ImagenService = ApiNet.imagenService
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

    suspend fun listar(): Result<List<Imagen>> =
        runCatching { service.listar().unwrap() }

    suspend fun obtener(id: Int): Result<Imagen> =
        runCatching { service.obtener(id).unwrap() }

    suspend fun crear(nuevo: Imagen): Result<Imagen> =
        runCatching { service.crear(nuevo).unwrap() }

    suspend fun actualizar(id: Int, datos: Imagen): Result<Imagen> =
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
