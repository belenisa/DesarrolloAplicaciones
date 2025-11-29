package com.example.evaluacion2.Data.network.Rol

import com.example.evaluacion2.Data.Modelo.EstadoVenta
import com.example.evaluacion2.Data.Modelo.Rol
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EstadoVentaService {

    @GET("api/estado_ventas")
    suspend fun listar(): Response<List<EstadoVenta>>

    @GET("api/estado_ventas/{id}")
    suspend fun obtener(@Path("id") id: Int): Response<EstadoVenta>

    @PUT("api/estado_ventas/{id}")
    suspend fun actualizar(@Path("id") id: Int, @Body datos: EstadoVenta): Response<EstadoVenta>

}