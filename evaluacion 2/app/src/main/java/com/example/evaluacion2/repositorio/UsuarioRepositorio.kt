package com.example.evaluacion2.repositorio


import com.example.evaluacion2.Data.network.UsuariosService
import com.example.evaluacion2.Data.network.usuario.UsuariosApi
import com.example.evaluacion2.Modelo.Usuarios
import retrofit2.Response

class UsuarioRepositorio(
    private val service: UsuariosService = UsuariosApi.retrofit.create(UsuariosService::class.java)
) {
    private fun <T> Response<T>.unwrap(): T {
        if (isSuccessful) {
            val body = body()
            if (body != null) return body
            throw Exception("Respuesta vacía del servidor")
        } else {
            val msg = errorBody()?.string()
            throw Exception("HTTP ${code()}: ${msg ?: "Error desconocido"}")
        }
    }

    suspend fun listar(): Result<List<Usuarios>> = runCatching { service.listar().unwrap() }
    suspend fun obtener(id: Int): Result<Usuarios> = runCatching { service.obtener(id).unwrap() }
    suspend fun crear(nuevo: Usuarios): Result<Usuarios> = runCatching { service.crear(nuevo).unwrap() }
    suspend fun actualizar(id: Int, datos: Usuarios): Result<Usuarios> = runCatching { service.actualizar(id, datos).unwrap() }
    suspend fun eliminar(id: Int): Result<Unit> = runCatching {
        val resp = service.eliminar(id)
        if (resp.isSuccessful) Unit else throw Exception("HTTP ${resp.code()}")
    }
}
