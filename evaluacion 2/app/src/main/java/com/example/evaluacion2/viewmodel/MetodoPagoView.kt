
package com.example.evaluacion2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaluacion2.Data.Modelo.MetodoPago
import com.example.evaluacion2.repositorio.MetodoPagoRepositorio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MetodoPagoView(
    private val repo: MetodoPagoRepositorio = MetodoPagoRepositorio()
) : ViewModel() {

    private val _metodos = MutableStateFlow<List<MetodoPago>>(emptyList())
    val metodos: StateFlow<List<MetodoPago>> = _metodos

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // --- Listar ---
    fun cargarMetodos() {
        viewModelScope.launch {
            _cargando.value = true
            try {
                repo.listar()
                    .onSuccess { lista ->
                        _metodos.value = lista
                        _error.value = null
                    }
                    .onFailure { e ->
                        _error.value = e.message ?: "No se pudieron cargar los métodos de pago"
                    }
            } finally {
                _cargando.value = false
            }
        }
    }

    // --- Obtener (opcional) ---
    fun obtenerMetodo(id: Int, onResult: (MetodoPago?) -> Unit) {
        viewModelScope.launch {
            if (id <= 0) {
                _error.value = "ID inválido para obtener método de pago"
                onResult(null)
                return@launch
            }
            repo.obtener(id)
                .onSuccess { metodo -> onResult(metodo) }
                .onFailure { e ->
                    _error.value = e.message ?: "Error al obtener método de pago"
                    onResult(null)
                }
        }
    }

    // --- Crear ---
    fun crearMetodo(metodoPago: String) {
        viewModelScope.launch {
            if (metodoPago.isBlank()) {
                _error.value = "El nombre del método de pago es obligatorio"
                return@launch
            }
            _cargando.value = true
            try {
                repo.crear(MetodoPago(metodoPago = metodoPago))
                    .onSuccess { creado ->
                        _metodos.value = _metodos.value + creado
                        _error.value = null
                    }
                    .onFailure { e ->
                        _error.value = e.message ?: "Error al crear método de pago"
                    }
            } finally {
                _cargando.value = false
            }
        }
    }

    // --- Actualizar ---
    fun actualizarMetodo(id: Int, metodoPago: String) {
        viewModelScope.launch {
            if (id <= 0) {
                _error.value = "ID inválido para actualizar"
                return@launch
            }
            if (metodoPago.isBlank()) {
                _error.value = "El nombre del método de pago es obligatorio"
                return@launch
            }
            _cargando.value = true
            try {
                repo.actualizar(id, MetodoPago(id = id, metodoPago = metodoPago))
                    .onSuccess { actualizado ->
                        val lista = _metodos.value.toMutableList()
                        val idx = lista.indexOfFirst { it.id == id }
                        if (idx >= 0) lista[idx] = actualizado
                        _metodos.value = lista
                        _error.value = null
                    }
                    .onFailure { e ->
                        _error.value = e.message ?: "Error al actualizar método de pago"
                    }
            } finally {
                _cargando.value = false
            }
        }
    }

    // --- Eliminar ---
    fun eliminarMetodo(id: Int) {
        viewModelScope.launch {
            if (id <= 0) {
                _error.value = "ID inválido para eliminar"
                return@launch
            }
            _cargando.value = true
            try {
                repo.eliminar(id)
                    .onSuccess {
                        _metodos.value = _metodos.value.filterNot { it.id == id }
                        _error.value = null
                    }
                    .onFailure { e ->
                        _error.value = e.message ?: "Error al eliminar método de pago"
                    }
            } finally {
                _cargando.value = false
            }
        }
    }
}
