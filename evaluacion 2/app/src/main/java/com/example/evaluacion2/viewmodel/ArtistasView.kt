
package com.example.evaluacion2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaluacion2.Data.Modelo.Artistas
import com.example.evaluacion2.Data.Modelo.Artista
import com.example.evaluacion2.Data.Modelo.Producto
import com.example.evaluacion2.repositorio.ArtistasRepositorio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArtistasView(
    private val repo: ArtistasRepositorio = ArtistasRepositorio()
) : ViewModel() {

    private val _artistas = MutableStateFlow<List<Artistas>>(emptyList())
    val artistas: StateFlow<List<Artistas>> = _artistas

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // --- Listar todas las relaciones ---
    fun cargarArtistas() {
        viewModelScope.launch {
            _cargando.value = true
            try {
                repo.listar()
                    .onSuccess { _artistas.value = it; _error.value = null }
                    .onFailure { e -> _error.value = e.message ?: "No se pudieron cargar las relaciones Artista-Producto" }
            } finally { _cargando.value = false }
        }
    }

    // --- Obtener relación por ID ---
    fun obtenerRelacion(id: Int, onResult: (Artistas?) -> Unit) {
        viewModelScope.launch {
            if (id <= 0) { _error.value = "ID inválido"; onResult(null); return@launch }
            repo.obtener(id)
                .onSuccess { onResult(it) }
                .onFailure { e -> _error.value = e.message ?: "Error al obtener relación"; onResult(null) }
        }
    }

    // --- Crear relación (Producto ↔ Artista) ---
    fun crearRelacion(producto: Producto, artista: Artista) {
        viewModelScope.launch {
            if (producto.id == null || artista.id == null) {
                _error.value = "Producto y Artista deben tener ID válido"
                return@launch
            }

            _cargando.value = true
            try {
                val nuevaRelacion = Artistas(id = null, artista = artista)
                repo.crear(nuevaRelacion)
                    .onSuccess { creada ->
                        _artistas.value = _artistas.value + creada
                        _error.value = null
                    }
                    .onFailure { e -> _error.value = e.message ?: "Error al crear relación" }
            } finally { _cargando.value = false }
        }
    }

    // --- Actualizar relación ---
    fun actualizarRelacion(id: Int, artista: Artista) {
        viewModelScope.launch {
            if (id <= 0 || artista.id == null) {
                _error.value = "ID inválido para actualizar relación"
                return@launch
            }

            _cargando.value = true
            try {
                val datos = Artistas(id = id, artista = artista)
                repo.actualizar(id, datos)
                    .onSuccess { actualizado ->
                        val lista = _artistas.value.toMutableList()
                        val idx = lista.indexOfFirst { it.id == id }
                        if (idx >= 0) lista[idx] = actualizado
                        _artistas.value = lista
                        _error.value = null
                    }
                    .onFailure { e -> _error.value = e.message ?: "Error al actualizar relación" }
            } finally { _cargando.value = false }
        }
    }

    // --- Eliminar relación ---
    fun eliminarRelacion(id: Int) {
        viewModelScope.launch {
            if (id <= 0) { _error.value = "ID inválido"; return@launch }
            _cargando.value = true
            try {
                repo.eliminar(id)
                    .onSuccess {
                        _artistas.value = _artistas.value.filterNot { it.id == id }
                        _error.value = null
                    }
                    .onFailure { e -> _error.value = e.message ?: "Error al eliminar relación" }
            } finally { _cargando.value = false }
        }
    }
}
