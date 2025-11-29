
package com.example.evaluacion2.Data.network

import com.example.evaluacion2.Data.Modelo.MetodoEnvio
import com.example.evaluacion2.Modelo.Usuarios
import retrofit2.http.*
import retrofit2.Response


interface MetodoEnvioService {

    @GET("api/metodo_envio")
    suspend fun listar(): Response<List<MetodoEnvio>>

    @GET("api/metodo_envio/{id}")
    suspend fun obtener(@Path("id") id: Int): Response<MetodoEnvio>

    @POST("api/metodo_envio")
    suspend fun crear(@Body nuevo: MetodoEnvio): Response<MetodoEnvio>

    @PUT("api/metodo_envio/{id}")
    suspend fun actualizar(@Path("id") id: Int, @Body datos: MetodoEnvio): Response<MetodoEnvio>

    @DELETE("api/metodo_envio/{id}")
    suspend fun eliminar(@Path("id") id: Int): Response<Unit>
}
