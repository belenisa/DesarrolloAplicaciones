
package com.example.evaluacion2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaluacion2.Data.Modelo.EstadoVenta
import com.example.evaluacion2.Data.Modelo.estados
import com.example.evaluacion2.repositorio.EstadoVentaRepositorio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EstadoVentaView(
    private val repo: EstadoVentaRepositorio = EstadoVentaRepositorio()
) : ViewModel() {

    private val _estadosVenta = MutableStateFlow<List<EstadoVenta>>(emptyList())
    val estadosVenta: StateFlow<List<EstadoVenta>> = _estadosVenta

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // --- Listar ---
    fun cargarEstados() {
        viewModelScope.launch {
            _cargando.value = true
            try {
                repo.listar()
                    .onSuccess { lista ->
                        _estadosVenta.value = lista
                        _error.value = null
                    }
                    .onFailure { e ->
                        _error.value = e.message ?: "No se pudieron cargar los estados de venta"
                    }
            } finally {
                _cargando.value = false
            }
        }
    }

    // --- Obtener ---
    fun obtenerEstado(id: Int, onResult: (EstadoVenta?) -> Unit) {
        viewModelScope.launch {
            if (id <= 0) {
                _error.value = "ID inválido para obtener estado de venta"
                onResult(null)
                return@launch
            }
            repo.obtener(id)
                .onSuccess { estado -> onResult(estado) }
                .onFailure { e ->
                    _error.value = e.message ?: "Error al obtener estado de venta"
                    onResult(null)
                }
        }
    }

    // --- Actualizar ---
    fun actualizarEstado(id: Int, nuevoEstado: estados) {
        viewModelScope.launch {
            if (id <= 0) {
                _error.value = "ID inválido para actualizar"
                return@launch
            }
            _cargando.value = true
            try {
                repo.actualizar(id, EstadoVenta(id = id, estado = nuevoEstado))
                    .onSuccess { actualizado ->
                        val lista = _estadosVenta.value.toMutableList()
                        val idx = lista.indexOfFirst { it.id == id }
                        if (idx >= 0) lista[idx] = actualizado
                        _estadosVenta.value = lista
                        _error.value = null
                    }
                    .onFailure { e ->
                        _error.value = e.message ?: "Error al actualizar estado de venta"
                    }
            } finally {
                _cargando.value = false
            }
        }
    }
}
