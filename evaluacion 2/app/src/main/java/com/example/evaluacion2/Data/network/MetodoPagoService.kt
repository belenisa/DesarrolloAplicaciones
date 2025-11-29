
package com.example.evaluacion2.Data.network

import com.example.evaluacion2.Data.Modelo.MetodoPago
import com.example.evaluacion2.Modelo.Usuarios
import retrofit2.http.*
import retrofit2.Response


interface MetodoPagoService {

    @GET("api/metodo_pago")
    suspend fun listar(): Response<List<MetodoPago>>

    @GET("api/metodo_pago/{id}")
    suspend fun obtener(@Path("id") id: Int): Response<MetodoPago>

    @POST("api/metodo_pago")
    suspend fun crear(@Body nuevo: MetodoPago): Response<MetodoPago>

    @PUT("api/metodo_pago/{id}")
    suspend fun actualizar(@Path("id") id: Int, @Body datos: MetodoPago): Response<MetodoPago>

    @DELETE("api/metodo_pago/{id}")
    suspend fun eliminar(@Path("id") id: Int): Response<Unit>
}
