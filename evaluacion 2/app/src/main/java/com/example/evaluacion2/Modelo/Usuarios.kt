package com.example.evaluacion2.Modelo

import java.time.LocalDate

data class Usuarios(
    val Usuario: String,
    val Nombre: String,
    val Apellido: String,
    val correo: String,
    val FechaNacimiento: LocalDate?,
    val Contraseña: String,
    val TipoUsuario: String
)
