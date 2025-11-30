
package com.example.evaluacion2.viewmodel.Venta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaluacion2.Modelo.Venta
import com.example.evaluacion2.repositorio.VentaRepositorio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VentaView(
    private val repo: VentaRepositorio = VentaRepositorio()
) : ViewModel() {

    private val _ventas = MutableStateFlow<List<Venta>>(emptyList())
    val ventas: StateFlow<List<Venta>> = _ventas

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun cargarVentas() {
        viewModelScope.launch {
            _cargando.value = true
            try {
                repo.listar()
                    .onSuccess { list ->
                        _ventas.value = list
                        _error.value = null
                    }
                    .onFailure { e ->
                        _error.value = e.message ?: "Error al cargar ventas"
                    }
            } finally {
                _cargando.value = false
            }
        }
    }

    fun crearVenta(nuevo: Venta) {
        viewModelScope.launch {
            repo.crear(nuevo)
                .onSuccess {
                    _error.value = null
                    cargarVentas()
                }
                .onFailure { e ->
                    _error.value = e.message ?: "Error al crear venta"
                }
        }
    }

    fun actualizarVenta(id: Int, datos: Venta) {
        viewModelScope.launch {
            if (id <= 0) {
                _error.value = "ID inválido para actualizar venta"
                return@launch
            }
            repo.actualizar(id, datos)
                .onSuccess {
                    _error.value = null
                    cargarVentas()
                }
                .onFailure { e ->
                    _error.value = e.message ?: "Error al actualizar venta"
                }
        }
    }

    fun eliminarVenta(id: Int) {
        viewModelScope.launch {
            if (id <= 0) {
                _error.value = "ID inválido para eliminar venta"
                return@launch
            }
            repo.eliminar(id)
                .onSuccess {
                    _error.value = null
                    cargarVentas()
                }
                .onFailure { e ->
                    _error.value = e.message ?: "Error al eliminar venta"
                }
        }
    }
}
