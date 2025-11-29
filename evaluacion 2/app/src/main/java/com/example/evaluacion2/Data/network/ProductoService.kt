
package com.example.evaluacion2.Data.network

import com.example.evaluacion2.Data.Modelo.Producto
import com.example.evaluacion2.Modelo.Usuarios
import retrofit2.http.*
import retrofit2.Response


interface ProductoService {

    @GET("api/productos")
    suspend fun listar(): Response<List<Producto>>

    @GET("api/productos/{id}")
    suspend fun obtener(@Path("id") id: Int): Response<Producto>

    @POST("api/productos")
    suspend fun crear(@Body nuevo: Producto): Response<Producto>

    @PUT("api/productos/{id}")
    suspend fun actualizar(@Path("id") id: Int, @Body datos: Producto): Response<Producto>

    @DELETE("api/productos/{id}")
    suspend fun eliminar(@Path("id") id: Int): Response<Unit>
}
