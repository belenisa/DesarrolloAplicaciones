
package com.example.evaluacion2.Data.network

import com.example.evaluacion2.Modelo.Usuarios
import retrofit2.http.*
import retrofit2.Response


interface RegionService {

    @GET("api/region")
    suspend fun listar(): Response<List<Region>>

    @GET("api/region/{id}")
    suspend fun obtener(@Path("id") id: Int): Response<Region>

    @POST("api/region")
    suspend fun crear(@Body nuevo: Region): Response<Region>

    @PUT("api/region/{id}")
    suspend fun actualizar(@Path("id") id: Int, @Body datos: Region): Response<Region>

    @DELETE("api/region/{id}")
    suspend fun eliminar(@Path("id") id: Int): Response<Unit>
}
