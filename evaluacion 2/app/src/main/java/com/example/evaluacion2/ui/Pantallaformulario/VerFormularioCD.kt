package com.example.evaluacion2.ui.Pantallaformulario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.evaluacion2.Modelo.CD
import com.example.evaluacion2.Modelo.CDsDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.example.evaluacion2.R


class VerFormularioCD(private val dao: CDsDao) : ViewModel() {

    val productos = dao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )


    fun agregarCD(
        autor: String,
        titulo: String,
        precio: Int,
        anio: Int,
        genero: String,
        imagenUri: String?,
        imagenResId: Int? = null
    ) {
        val nuevoCD = CD(
            autor = autor.trim(),
            anio = anio,
            titulo = titulo.trim(),
            genero = genero,
            precio = precio,
            imagenResId = imagenResId,
            imagenUri = imagenUri
        )

        viewModelScope.launch {
            dao.upsert(nuevoCD)
        }
    }


    fun limpiar() {
        viewModelScope.launch {
            dao.clear()
        }
    }

    fun insertarCatalogoInicial() {
        val cdsIniciales = listOf(
            CD(1, "The Cure", 1982, "Pornography", "Rock", 12990, R.drawable.cure, null),
            CD(2, "Daft Punk", 2001, "Discovery", "Electrónica", 14990, R.drawable.daftpunk, null),
            CD(3, "The Doors", 1967, "Strange Days", "Rock", 10990, R.drawable.doors, null),
            CD(4, "Kids See Ghosts", 2018, "Kids See Ghosts", "Hip-hop", 14990, R.drawable.ksg, null),
            CD(5, "Low Roar", 2017, "Once in a Long, Long While", "Rock", 10990, R.drawable.lowroar, null),
            CD(6, "Depeche Mode", 1990, "Violator", "Rock", 13990, R.drawable.violator, null)
        )

        viewModelScope.launch {
            dao.insertarVarios(cdsIniciales)
        }
    }
}

class VerFormularioCDFactory(private val dao: CDsDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VerFormularioCD::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VerFormularioCD(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
