package com.example.evaluacion2.Data.network.Rol

import com.example.evaluacion2.Data.Modelo.Rol
import com.example.evaluacion2.Modelo.Venta
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface VentaServive {

    @GET("api/ventas")
    suspend fun listar(): Response<List<Venta>>

    @GET("api/ventas/{id}")
    suspend fun obtener(@Path("id") id: Int): Response<Venta>

    @POST("api/ventas")
    suspend fun crear(@Body nuevo: Venta): Response<Venta>

    @PUT("api/ventas/{id}")
    suspend fun actualizar(@Path("id") id: Int, @Body datos: Venta): Response<Venta>

    @DELETE("api/ventas/{id}")
    suspend fun eliminar(@Path("id") id: Int): Response<Unit>
}