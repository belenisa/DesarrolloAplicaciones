
package com.example.evaluacion2.Data.network

import com.example.evaluacion2.Modelo.Usuarios
import retrofit2.http.*
import retrofit2.Response


interface ComunaService {

    @GET("api/comuna")
    suspend fun listar(): Response<List<Comuna>>

    @GET("api/comuna/{id}")
    suspend fun obtener(@Path("id") id: Int): Response<Comuna>

    @POST("api/comuna")
    suspend fun crear(@Body nuevo: Comuna): Response<Comuna>

    @PUT("api/comuna/{id}")
    suspend fun actualizar(@Path("id") id: Int, @Body datos: Comuna): Response<Comuna>

    @DELETE("api/comuna/{id}")
    suspend fun eliminar(@Path("id") id: Int): Response<Unit>
}
