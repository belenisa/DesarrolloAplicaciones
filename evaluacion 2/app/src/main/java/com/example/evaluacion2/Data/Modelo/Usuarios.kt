package com.example.evaluacion2.Modelo

import com.example.evaluacion2.Data.Modelo.Direccion
import com.example.evaluacion2.Data.Modelo.Rol
import com.google.gson.annotations.SerializedName

data class Usuarios(
    val id: Int? = null,
    val nombre: String,
    val correo: String,
    val contrasena: String? = null, // solo para enviar en POST/PUT
    val direcciones: Direccion? = null,
    val rol: Rol? = null
)
