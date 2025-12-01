
package com.example.evaluacion2.viewmodel.TipoProducto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaluacion2.Data.Modelo.Region
import com.example.evaluacion2.repositorio.RegionRepositorio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TipoProductoView(
    private val repo: TipoProductoRepositorio = TipoProductoRepositorio()
) : ViewModel() {

    private val _tipoProducto = MutableStateFlow<List<TipoProducto>>(emptyList())
    val productos: StateFlow<List<TipoProducto>> = _tipoProducto

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun cargarRegiones() {
        viewModelScope.launch {
            _cargando.value = true
            try {
                repo.listar()
                    .onSuccess { lista ->
                        _tipoProducto.value = lista
                        _error.value = null
                    }
                    .onFailure { e ->
                        _error.value = e.message ?: "No se pudieron cargar las regiones"
                    }
            } finally {
                _cargando.value = false
            }
        }
    }

    fun obtenerTipoProducto(id: Int, onResult: (TipoProducto?) -> Unit) {
        viewModelScope.launch {
            if (id <= 0) {
                _error.value = "ID inválido para obtener región"
                onResult(null)
                return@launch
            }
            repo.obtener(id)
                .onSuccess { productos -> onResult(region) }
                .onFailure { e ->
                    _error.value = e.message ?: "Error al obtener región"
                    onResult(null)
                }
        }
    }

    fun crearTipoProducto(nombre: String) {
        viewModelScope.launch {
            if (nombre.isBlank()) {
                _error.value = "El nombre de la región es obligatorio"
                return@launch
            }
            _cargando.value = true
            try {
                repo.crear(TipoProducto(nombre = nombre))
                    .onSuccess { creada ->
                        val actual = _tipoProducto.value.toMutableList()
                        actual.add(creada)
                        _tipoProducto.value = actual
                        _error.value = null
                    }
                    .onFailure { e ->
                        _error.value = e.message ?: "Error al crear región"
                    }
            } finally {
                _cargando.value = false
            }
        }
    }

    fun actualizarTipoProducto(id: Int, nombre: String) {
        viewModelScope.launch {
            if (id <= 0) {
                _error.value = "ID inválido para actualizar"
                return@launch
            }
            if (nombre.isBlank()) {
                _error.value = "El nombre de la región es obligatorio"
                return@launch
            }
            _cargando.value = true
            try {
                repo.actualizar(id, TipoProducto(id = id, nombre = nombre))
                    .onSuccess { actualizada ->
                        val actual = _tipoProducto.value.toMutableList()
                        val idx = actual.indexOfFirst { it.id == id }
                        if (idx >= 0) actual[idx] = actualizada
                        _tipoProducto.value = actual
                        _error.value = null
                    }
                    .onFailure { e ->
                        _error.value = e.message ?: "Error al actualizar región"
                    }
            } finally {
                _cargando.value = false
            }
        }
    }

    fun eliminarTipoProducto(id: Int) {
        viewModelScope.launch {
            if (id <= 0) {
                _error.value = "ID inválido para eliminar"
                return@launch
            }
            _cargando.value = true
            try {
                repo.eliminar(id)
                    .onSuccess {
                        _tipoProducto.value = _tipoProducto.value.filterNot { it.id == id }
                        _error.value = null
                    }
                    .onFailure { e ->
                        _error.value = e.message ?: "Error al eliminar región"
                    }
            } finally {
                _cargando.value = false
            }
        }
    }
}
