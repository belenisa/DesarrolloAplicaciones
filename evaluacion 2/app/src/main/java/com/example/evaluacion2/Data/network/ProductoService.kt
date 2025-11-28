
package com.example.evaluacion2.Data.network

import com.example.evaluacion2.Modelo.Usuarios
import retrofit2.http.*
import retrofit2.Response


interface ProductoService {

    @GET("api/productos")
    suspend fun listar(): Response<List<Prductos>>

    @GET("api/productos/{id}")
    suspend fun obtener(@Path("id") id: Int): Response<Prductos>

    @POST("api/productos")
    suspend fun crear(@Body nuevo: Prductos): Response<Prductos>

    @PUT("api/productos/{id}")
    suspend fun actualizar(@Path("id") id: Int, @Body datos: Prductos): Response<Prductos>

    @DELETE("api/productos/{id}")
    suspend fun eliminar(@Path("id") id: Int): Response<Unit>
}
