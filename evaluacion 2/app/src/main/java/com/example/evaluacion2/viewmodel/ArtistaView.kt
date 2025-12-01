
package com.example.evaluacion2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaluacion2.Data.Modelo.Artista
import com.example.evaluacion2.repositorio.ArtistaRepositorio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArtistaViewModel(
    private val repo: ArtistaRepositorio = ArtistaRepositorio()
) : ViewModel() {

    private val _artistas = MutableStateFlow<List<Artista>>(emptyList())
    val artistas: StateFlow<List<Artista>> = _artistas

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // --- Listar ---
    fun cargarArtistas() {
        viewModelScope.launch {
            _cargando.value = true
            try {
                repo.listar()
                    .onSuccess { _artistas.value = it; _error.value = null }
                    .onFailure { e -> _error.value = e.message ?: "No se pudieron cargar los artistas" }
            } finally { _cargando.value = false }
        }
    }

    // --- Obtener ---
    fun obtenerArtista(id: Int, onResult: (Artista?) -> Unit) {
        viewModelScope.launch {
            if (id <= 0) { _error.value = "ID inválido"; onResult(null); return@launch }
            repo.obtener(id)
                .onSuccess { onResult(it) }
                .onFailure { e -> _error.value = e.message ?: "Error al obtener artista"; onResult(null) }
        }
    }

    // --- Crear ---
    fun crearArtista(nombre: String) {
        viewModelScope.launch {
            if (nombre.isBlank()) { _error.value = "El nombre es obligatorio"; return@launch }

            _cargando.value = true
            try {
                val nuevo = Artista(id = null, nombre = nombre)
                repo.crear(nuevo)
                    .onSuccess { creado ->
                        _artistas.value = _artistas.value + creado
                        _error.value = null
                    }
                    .onFailure { e -> _error.value = e.message ?: "Error al crear artista" }
            } finally { _cargando.value = false }
        }
    }

    // --- Actualizar ---
    fun actualizarArtista(id: Int, nombre: String) {
        viewModelScope.launch {
            if (id <= 0) { _error.value = "ID inválido"; return@launch }
            if (nombre.isBlank()) { _error.value = "El nombre es obligatorio"; return@launch }

            _cargando.value = true
            try {
                val datos = Artista(id = id, nombre = nombre)
                repo.actualizar(id, datos)
                    .onSuccess { actualizado ->
                        val lista = _artistas.value.toMutableList()
                        val idx = lista.indexOfFirst { it.id == id }
                        if (idx >= 0) lista[idx] = actualizado
                        _artistas.value = lista
                        _error.value = null
                    }
                    .onFailure { e -> _error.value = e.message ?: "Error al actualizar artista" }
            } finally { _cargando.value = false }
        }
    }

    // --- Eliminar ---
    fun eliminarArtista(id: Int) {
        viewModelScope.launch {
            if (id <= 0) { _error.value = "ID inválido"; return@launch }
            _cargando.value = true
            try {
                repo.eliminar(id)
                    .onSuccess {
                        _artistas.value = _artistas.value.filterNot { it.id == id }
                        _error.value = null
                    }
                    .onFailure { e -> _error.value = e.message ?: "Error al eliminar artista" }
            } finally { _cargando.value = false }
        }
    }
}
