package com.example.evaluacion2.ui.Pantalla

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.evaluacion2.Modelo.CD
import com.example.evaluacion2.Modelo.CDsDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.example.evaluacion2.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest


class VerFormularioCD(private val dao: CDsDao) : ViewModel() {

    val productos = dao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
    val primerosTresCDs: StateFlow<List<CD>> = dao.getPrimerosTresCDs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    private val _query = MutableStateFlow("")
    val resultadosBusqueda: StateFlow<List<CD>> = _query
        .debounce(300) // espera 300ms antes de buscar
        .flatMapLatest { query ->
            dao.buscarProductos(query)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList()
        )


    init {
        viewModelScope.launch {
            dao.clear() // Limpia la tabla
            insertarCatalogoInicial() // Inserta los datos iniciales
        }
    }

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

    fun eliminarCD(cd: CD) {
        viewModelScope.launch {
            dao.eliminarCD(cd)
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

    fun actualizarBusqueda(nuevoTexto: String) {
        _query.value = nuevoTexto
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

