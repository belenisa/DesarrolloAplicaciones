
package com.example.evaluacion2.Data.network

import com.example.evaluacion2.Data.Modelo.Artista
import com.example.evaluacion2.Modelo.Usuarios
import retrofit2.http.*
import retrofit2.Response


interface ArtistaService {

    @GET("api/artista")
    suspend fun listar(): Response<List<Artista>>

    @GET("api/artista/{id}")
    suspend fun obtener(@Path("id") id: Int): Response<Artista>

    @POST("api/artista")
    suspend fun crear(@Body nuevo: Artista): Response<Artista>

    @PUT("api/artista/{id}")
    suspend fun actualizar(@Path("id") id: Int, @Body datos: Artista): Response<Artista>

    @DELETE("api/artista/{id}")
    suspend fun eliminar(@Path("id") id: Int): Response<Unit>
}
