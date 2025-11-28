package com.example.evaluacion2.Data.network.Rol

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RolApi {

    private const val BASE_URL = "https://ragemusicbackend.onrender.com/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    } /*Level.BODY imprime headers y cuerpo de las peticiones y respuestas en Logcat (útil para depurar).
        En producción conviene bajarlo a BASIC o no usarlo (para no exponer datos sensibles ni afectar rendimiento).*/

    private val okHttp = OkHttpClient.Builder()  /*es el cliente HTTP que usa Retrofi*/
        .addInterceptor(logging)
        .build()

    val retrofit: Retrofit = Retrofit.Builder() /*registra un GsonConverterFactory para convertir JSON a objetos*/
        .baseUrl(BASE_URL)
        .client(okHttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}