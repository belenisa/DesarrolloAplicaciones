
package com.example.evaluacion2.repositorio

import com.example.evaluacion2.Data.network.UsuariosService
import com.example.evaluacion2.Modelo.Usuarios

class AuthRepositorio(private val service: UsuariosService) {
    suspend fun login(correo: String, contrasena: String): Result<Usuarios> = runCatching {
        val response = service.listar()
        if (response.isSuccessful) {
            val usuarios = response.body() ?: emptyList()

            val usuario = usuarios.find { it.correo.equals(correo, ignoreCase = true) }

            usuario ?: throw Exception("Usuario no encontrado")
        } else {
            throw Exception("Error ${response.code()}: ${response.message()}")
        }
    }
}
