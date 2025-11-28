package com.example.evaluacion2.Modelo

import com.example.evaluacion2.Data.Modelo.Direccion
import com.example.evaluacion2.Data.Modelo.Rol

data class Usuarios(
    val id: Int? = null,
    val nombre: String,
    val correo: String,
    val Contrasena: String? = null, // solo para enviar en POST/PUT
    val direcciones: Direccion? = null,
    val rol: Rol? = null
)
