
package com.example.evaluacion2.Data.network

import com.example.evaluacion2.Data.Modelo.Direccion
import com.example.evaluacion2.Modelo.Usuarios
import retrofit2.http.*
import retrofit2.Response


interface DireccionService {

    @GET("api/direccion")
    suspend fun listar(): Response<List<Direccion>>

    @GET("api/direccion/{id}")
    suspend fun obtener(@Path("id") id: Int): Response<Direccion>

    @POST("api/direccion")
    suspend fun crear(@Body nuevo: Direccion): Response<Direccion>

    @PUT("api/direccion/{id}")
    suspend fun actualizar(@Path("id") id: Int, @Body datos: Direccion): Response<Direccion>

    @DELETE("api/direccion/{id}")
    suspend fun eliminar(@Path("id") id: Int): Response<Unit>
}
