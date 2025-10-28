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
                Usuarios("JuanitoAD","Juan","Perez","juanperez@gmail.com",
                    LocalDate.of(1980, 7, 12),"123","Admin"),
                Usuarios("PedroCl","Pedro","Lopez","pedrolopez@gmail.com",
                    LocalDate.of(1970,4,4),"123","Cliente")
                )
        )
    }

    fun mostrarUsuarios(): List<Usuarios> = listaUsuarios
}