
package com.example.evaluacion2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaluacion2.Data.Modelo.Comuna
import com.example.evaluacion2.Data.Modelo.Direccion
import com.example.evaluacion2.repositorio.DireccionRepositorio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DireccionView(
    private val repo: DireccionRepositorio = DireccionRepositorio()
) : ViewModel() {

    private val _direcciones = MutableStateFlow<List<Direccion>>(emptyList())
    val direcciones: StateFlow<List<Direccion>> = _direcciones

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // --- Listar ---
    fun cargarDirecciones() {
        viewModelScope.launch {
            _cargando.value = true
            try {
                repo.listar()
                    .onSuccess { _direcciones.value = it; _error.value = null }
                    .onFailure { e -> _error.value = e.message ?: "No se pudieron cargar las direcciones" }
            } finally { _cargando.value = false }
        }
    }

    // --- Obtener ---
    fun obtenerDireccion(id: Int, onResult: (Direccion?) -> Unit) {
        viewModelScope.launch {
            if (id <= 0) { _error.value = "ID inválido"; onResult(null); return@launch }
            repo.obtener(id)
                .onSuccess { onResult(it) }
                .onFailure { e -> _error.value = e.message ?: "Error al obtener dirección"; onResult(null) }
        }
    }

    // --- Crear ---
    fun crearDireccion(direccion: String, codigoPostal: Int, comunaId: Int?) {
        viewModelScope.launch {
            if (direccion.isBlank()) { _error.value = "La dirección es obligatoria"; return@launch }
            if (codigoPostal <= 0)   { _error.value = "Código postal inválido"; return@launch }

            _cargando.value = true
            try {
                val comunaObj = comunaId?.let { Comuna(id = it, nombre = "") } // Ajusta si Comuna requiere más campos
                val nueva = Direccion(direccion = direccion, codigoPostal = codigoPostal, comuna = comunaObj)
                repo.crear(nueva)
                    .onSuccess { creada -> _direcciones.value = _direcciones.value + creada; _error.value = null }
                    .onFailure { e -> _error.value = e.message ?: "Error al crear dirección" }
            } finally { _cargando.value = false }
        }
    }

    // --- Actualizar ---
    fun actualizarDireccion(id: Int, direccion: String, codigoPostal: Int, comunaId: Int?) {
        viewModelScope.launch {
            if (id <= 0) { _error.value = "ID inválido"; return@launch }
            if (direccion.isBlank()) { _error.value = "La dirección es obligatoria"; return@launch }
            if (codigoPostal <= 0)   { _error.value = "Código postal inválido"; return@launch }

            _cargando.value = true
            try {
                val comunaObj = comunaId?.let { Comuna(id = it, nombre = "") }
                val datos = Direccion(id = id, direccion = direccion, codigoPostal = codigoPostal, comuna = comunaObj)
                repo.actualizar(id, datos)
                    .onSuccess { actualizado ->
                        val lista = _direcciones.value.toMutableList()
                        val idx = lista.indexOfFirst { it.id == id }
                        if (idx >= 0) lista[idx] = actualizado
                        _direcciones.value = lista
                        _error.value = null
                    }
                    .onFailure { e -> _error.value = e.message ?: "Error al actualizar dirección" }
            } finally { _cargando.value = false }
        }
    }

    // --- Eliminar ---
    fun eliminarDireccion(id: Int) {
        viewModelScope.launch {
            if (id <= 0) { _error.value = "ID inválido"; return@launch }
            _cargando.value = true
            try {
                repo.eliminar(id)
                    .onSuccess {
                        _direcciones.value = _direcciones.value.filterNot { it.id == id }
                        _error.value = null
                    }
                    .onFailure { e -> _error.value = e.message ?: "Error al eliminar dirección" }
            } finally { _cargando.value = false }
        }
    }
}
