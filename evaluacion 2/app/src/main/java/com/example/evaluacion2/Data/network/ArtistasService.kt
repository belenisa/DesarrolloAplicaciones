
package com.example.evaluacion2.Data.network

import com.example.evaluacion2.Modelo.Usuarios
import retrofit2.http.*
import retrofit2.Response


interface ArtistasService {

    @GET("api/artistas")
    suspend fun listar(): Response<List<Artistas>>

    @GET("api/artistas/{id}")
    suspend fun obtener(@Path("id") id: Int): Response<Artistas>

    @POST("api/artistas")
    suspend fun crear(@Body nuevo: Artistas): Response<Artistas>

    @PUT("api/artistas/{id}")
    suspend fun actualizar(@Path("id") id: Int, @Body datos: Artistas): Response<Artistas>

    @DELETE("api/artistas/{id}")
    suspend fun eliminar(@Path("id") id: Int): Response<Unit>
}
