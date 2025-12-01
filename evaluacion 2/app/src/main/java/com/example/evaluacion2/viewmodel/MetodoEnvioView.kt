
package com.example.evaluacion2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaluacion2.Data.Modelo.MetodoEnvio
import com.example.evaluacion2.repositorio.MetodoEnvioRepositorio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MetodoEnvioView(
    private val repo: MetodoEnvioRepositorio = MetodoEnvioRepositorio()
) : ViewModel() {

    private val _metodos = MutableStateFlow<List<MetodoEnvio>>(emptyList())
    val metodos: StateFlow<List<MetodoEnvio>> = _metodos

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // Listar
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
                        _error.value = e.message ?: "No se pudieron cargar los métodos de envío"
                    }
            } finally {
                _cargando.value = false
            }
        }
    }

    // Obtener (opcional)
    fun obtenerMetodo(id: Int, onResult: (MetodoEnvio?) -> Unit) {
        viewModelScope.launch {
            if (id <= 0) {
                _error.value = "ID inválido para obtener método de envío"
                onResult(null)
                return@launch
            }
            repo.obtener(id)
                .onSuccess { metodo -> onResult(metodo) }
                .onFailure { e ->
                    _error.value = e.message ?: "Error al obtener método de envío"
                    onResult(null)
                }
        }
    }

    // Crear
    fun crearMetodo(metodoEnvio: String) {
        viewModelScope.launch {
            if (metodoEnvio.isBlank()) {
                _error.value = "El nombre del método de envío es obligatorio"
                return@launch
            }
            _cargando.value = true
            try {
                repo.crear(MetodoEnvio(metodoEnvio = metodoEnvio))
                    .onSuccess { creado ->
                        _metodos.value = _metodos.value + creado
                        _error.value = null
                    }
                    .onFailure { e ->
                        _error.value = e.message ?: "Error al crear método de envío"
                    }
            } finally {
                _cargando.value = false
            }
        }
    }

    // Actualizar
    fun actualizarMetodo(id: Int, metodoEnvio: String) {
        viewModelScope.launch {
            if (id <= 0) {
                _error.value = "ID inválido para actualizar"
                return@launch
            }
            if (metodoEnvio.isBlank()) {
                _error.value = "El nombre del método de envío es obligatorio"
                return@launch
            }
            _cargando.value = true
            try {
                repo.actualizar(id, MetodoEnvio(id = id, metodoEnvio = metodoEnvio))
                    .onSuccess { actualizado ->
                        val lista = _metodos.value.toMutableList()
                        val idx = lista.indexOfFirst { it.id == id }
                        if (idx >= 0) lista[idx] = actualizado
                        _metodos.value = lista
                        _error.value = null
                    }
                    .onFailure { e ->
                        _error.value = e.message ?: "Error al actualizar método de envío"
                    }
            } finally {
                _cargando.value = false
            }
        }
    }

    // Eliminar
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
                        _error.value = e.message ?: "Error al eliminar método de envío"
                    }
            } finally {
                _cargando.value = false
            }
        }
    }
}
