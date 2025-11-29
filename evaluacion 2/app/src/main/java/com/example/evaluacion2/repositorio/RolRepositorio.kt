
package com.example.evaluacion2.repositorio

import com.example.evaluacion2.Data.Modelo.Rol
import com.example.evaluacion2.Data.network.Rol.RolService


class RolRepositorio(
    private val service: RolService = ApiNet.rolService
) {
    suspend fun listarRoles(): List<Rol>? {
        val response = service.listar()
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun obtenerRol(id: Int): Rol? {
        val response = service.obtener(id)
        return if (response.isSuccessful) response.body() else null
    }
}
