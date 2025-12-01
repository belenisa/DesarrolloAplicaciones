package com.example.evaluacion2.Data.network

import com.example.evaluacion2.Data.Modelo.TipoProducto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TipoProductoService {

    @GET("api/tipo-producto")
    suspend fun listar(): Response<List<TipoProducto>>

    @GET("api/tipo-producto/{id}")
    suspend fun obtener(@Path("id") id: Int): Response<TipoProducto>

    @POST("api/tipo-producto")
    suspend fun crear(@Body nuevo: TipoProducto): Response<TipoProducto>

    @PUT("api/tipo-producto/{id}")
    suspend fun actualizar(@Path("id") id: Int, @Body datos: TipoProducto): Response<TipoProducto>

    @DELETE("api/tipo-producto/{id}")
    suspend fun eliminar(@Path("id") id: Int): Response<Unit>

}