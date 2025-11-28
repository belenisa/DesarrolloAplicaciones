
package com.example.evaluacion2.Data.network

import com.example.evaluacion2.Modelo.Usuarios
import retrofit2.http.*
import retrofit2.Response


interface UsuariosService {

    @GET("api/usuarios")
    suspend fun listar(): Response<List<Usuarios>>

    @GET("api/usuarios/{id}")
    suspend fun obtener(@Path("id") id: Int): Response<Usuarios>

    @POST("api/usuarios")
    suspend fun crear(@Body nuevo: Usuarios): Response<Usuarios>

    @PUT("api/usuarios/{id}")
    suspend fun actualizar(@Path("id") id: Int, @Body datos: Usuarios): Response<Usuarios>

    @DELETE("api/usuarios/{id}")
    suspend fun eliminar(@Path("id") id: Int): Response<Unit>
}
