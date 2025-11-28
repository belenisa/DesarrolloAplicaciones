package com.example.evaluacion2.Data.network.Rol

import com.example.evaluacion2.Data.Modelo.Rol
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RolService {

    @GET("api/rolusuarios")
    suspend fun listar(): Response<List<Rol>>

    @GET("api/rolusuarios/{id}")
    suspend fun obtener(@Path("id") id: Int): Response<Rol>

    @POST("api/rolusuarios")
    suspend fun crear(@Body nuevo: Rol): Response<Rol>

    @PUT("api/rolusuarios/{id}")
    suspend fun actualizar(@Path("id") id: Int, @Body datos: Rol): Response<Rol>

    @DELETE("api/rolusuarios/{id}")
    suspend fun eliminar(@Path("id") id: Int): Response<Unit>

}