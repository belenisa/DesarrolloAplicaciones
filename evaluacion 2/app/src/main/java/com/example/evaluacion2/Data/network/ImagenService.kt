
package com.example.evaluacion2.Data.network

import com.example.evaluacion2.Data.Modelo.Imagen
import com.example.evaluacion2.Modelo.Usuarios
import retrofit2.http.*
import retrofit2.Response


interface ImagenService {

    @GET("api/imagen")
    suspend fun listar(): Response<List<Imagen>>

    @GET("api/imagen/{id}")
    suspend fun obtener(@Path("id") id: Int): Response<Imagen>

    @POST("api/imagen")
    suspend fun crear(@Body nuevo: Imagen): Response<Imagen>

    @PUT("api/imagen/{id}")
    suspend fun actualizar(@Path("id") id: Int, @Body datos: Imagen): Response<Imagen>

    @DELETE("api/imagen/{id}")
    suspend fun eliminar(@Path("id") id: Int): Response<Unit>
}
