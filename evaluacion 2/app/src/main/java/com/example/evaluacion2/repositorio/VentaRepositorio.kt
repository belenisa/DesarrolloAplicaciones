
package com.example.evaluacion2.repositorio


import com.example.evaluacion2.Data.network.Rol.VentaServive
import com.example.evaluacion2.Modelo.Venta

class VentaRepositorio(
    private val service: VentaServive = ApiNet.ventaService
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
