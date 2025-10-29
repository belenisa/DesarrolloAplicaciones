package com.example.evaluacion2.Data


import android.os.Build
import androidx.annotation.RequiresApi
import com.example.evaluacion2.Modelo.Usuarios
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
class ListaUsuarios {

    private val listaUsuarios = mutableListOf<Usuarios>()

    init {
        listaUsuarios.addAll(
            listOf(
                Usuarios(
                    Usuario = "JuanitoAD",
                    Nombre = "Juan",
                    Apellido = "Perez",
                    correo = "juanperez@gmail.com",
                    FechaNacimiento = LocalDate.of(1980, 7, 12),
                    Contraseña = "123",
                    TipoUsuario = "Admin"
                ),
                Usuarios(
                    Usuario = "PedroCl",
                    Nombre = "Pedro",
                    Apellido = "Lopez",
                    correo = "pedrolopez@gmail.com",
                    FechaNacimiento = LocalDate.of(1970, 4, 4),
                    Contraseña = "123",
                    TipoUsuario = "Cliente"
                )
            )
        )
    }

    fun obtenerUsuarios(): List<Usuarios> {
        return listaUsuarios
    }

    fun agregarUsuario(usuario: Usuarios) {
        listaUsuarios.add(usuario)
    }
}
