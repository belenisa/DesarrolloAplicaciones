
package com.example.evaluacion2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaluacion2.Data.Modelo.Comuna
import com.example.evaluacion2.repositorio.ComunaRepositorio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ComunaView(
    private val repo: ComunaRepositorio = ComunaRepositorio()
) : ViewModel() {

    private val _comunas = MutableStateFlow<List<Comuna>>(emptyList())
    val comunas: StateFlow<List<Comuna>> = _comunas

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // --- Listar ---
    fun cargarComunas() {
        viewModelScope.launch {
            _cargando.value = true
            try {
                repo.listar()
                    .onSuccess { _comunas.value = it; _error.value = null }
                    .onFailure { e -> _error.value = e.message ?: "No se pudieron cargar las comunas" }
            } finally { _cargando.value = false }
        }
    }

    // --- Obtener ---
    fun obtenerComuna(id: Int, onResult: (Comuna?) -> Unit) {
        viewModelScope.launch {
            if (id <= 0) { _error.value = "ID inválido"; onResult(null); return@launch }
            repo.obtener(id)
                .onSuccess { onResult(it) }
                .onFailure { e -> _error.value = e.message ?: "Error al obtener comuna"; onResult(null) }
        }
    }

    // --- Crear ---
    fun crearComuna(nombre: String) {
        viewModelScope.launch {
            if (nombre.isBlank()) { _error.value = "El nombre es obligatorio"; return@launch }

            _cargando.value = true
            try {
                val nueva = Comuna(id = null, nombre = nombre)
                repo.crear(nueva)
                    .onSuccess { creada ->
                        _comunas.value = _comunas.value + creada
                        _error.value = null
                    }
                    .onFailure { e -> _error.value = e.message ?: "Error al crear comuna" }
            } finally { _cargando.value = false }
        }
    }

    // --- Actualizar ---
    fun actualizarComuna(id: Int, nombre: String) {
        viewModelScope.launch {
            if (id <= 0) { _error.value = "ID inválido"; return@launch }
            if (nombre.isBlank()) { _error.value = "El nombre es obligatorio"; return@launch }

            _cargando.value = true
            try {
                val datos = Comuna(id = id, nombre = nombre)
                repo.actualizar(id, datos)
                    .onSuccess { actualizado ->
                        val lista = _comunas.value.toMutableList()
                        val idx = lista.indexOfFirst { it.id == id }
                        if (idx >= 0) lista[idx] = actualizado
                        _comunas.value = lista
                        _error.value = null
                    }
                    .onFailure { e -> _error.value = e.message ?: "Error al actualizar comuna" }
            } finally { _cargando.value = false }
        }
    }

    // --- Eliminar ---
    fun eliminarComuna(id: Int) {
        viewModelScope.launch {
            if (id <= 0) { _error.value = "ID inválido"; return@launch }
            _cargando.value = true
            try {
                repo.eliminar(id)
                    .onSuccess {
                        _comunas.value = _comunas.value.filterNot { it.id == id }
                        _error.value = null
                    }
                    .onFailure { e -> _error.value = e.message ?: "Error al eliminar comuna" }
            } finally { _cargando.value = false }
        }
    }
}
