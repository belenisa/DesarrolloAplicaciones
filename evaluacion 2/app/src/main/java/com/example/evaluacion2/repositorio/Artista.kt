
package com.example.evaluacion2.repositorio

import com.example.evaluacion2.Data.Modelo.Artista
import com.example.evaluacion2.Data.network.ArtistaService
import retrofit2.Response

class ArtistaRepositorio(
    private val service: ArtistaService = ApiNet.artistaService
) {

    private fun <T> Response<T>.unwrap(): T {
        if (isSuccessful) {
            val b = body()
            if (b != null) return b
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

    suspend fun listar(): Result<List<Artista>> =
        runCatching { service.listar().unwrap() }

    suspend fun obtener(id: Int): Result<Artista> =
        runCatching { service.obtener(id).unwrap() }

    suspend fun crear(nuevo: Artista): Result<Artista> =
        runCatching { service.crear(nuevo).unwrap() }

    suspend fun actualizar(id: Int, datos: Artista): Result<Artista> =
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
