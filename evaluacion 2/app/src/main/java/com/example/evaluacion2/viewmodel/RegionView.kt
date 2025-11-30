
package com.example.evaluacion2.viewmodel.region

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaluacion2.Data.Modelo.Region
import com.example.evaluacion2.repositorio.RegionRepositorio
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Estado unificado para la UI
data class RegionView(
    val regiones: List<Region> = emptyList(),
    val cargando: Boolean = false,
    val error: String? = null
)

sealed class RegionEvent {
    data object Cargadas : RegionEvent()
    data object Creada : RegionEvent()
    data object Actualizada : RegionEvent()
    data object Eliminada : RegionEvent()
}

class RegionViewModel(
    private val repo: RegionRepositorio = RegionRepositorio()
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegionView())
    val uiState: StateFlow<RegionView> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<RegionEvent>()
    val events: SharedFlow<RegionEvent> = _events

    // Evita recargas innecesarias (cache simple)
    private var cacheLoaded = false

    // Helper para fijar error rápidamente
    private fun setError(msg: String) {
        _uiState.value = _uiState.value.copy(error = msg)
    }

    // --- Listar todas las regiones ---
    fun cargarRegiones(forceReload: Boolean = false) {
        viewModelScope.launch {
            if (cacheLoaded && !forceReload) return@launch
            _uiState.value = _uiState.value.copy(cargando = true, error = null)

            repo.listar()
                .onSuccess { lista ->
                    _uiState.value = _uiState.value.copy(
                        regiones = lista,
                        cargando = false,
                        error = null
                    )
                    cacheLoaded = true
                    _events.emit(RegionEvent.Cargadas)
                }
                .onFailure { e ->
                    _uiState.value = _uiState.value.copy(
                        cargando = false,
                        error = e.message ?: "No se pudieron cargar las regiones"
                    )
                }
        }
    }

    // --- Obtener una región específica (opcional: para detalle/editar) ---
    fun obtenerRegion(id: Int, onResult: (Region?) -> Unit) {
        viewModelScope.launch {
            if (id <= 0) {
                setError("ID inválido para obtener región")
                onResult(null)
                return@launch
            }
            // No cambiamos 'cargando' global para evitar parpadeo de la lista
            repo.obtener(id)
                .onSuccess { region -> onResult(region) }
                .onFailure { e ->
                    setError(e.message ?: "Error al obtener región")
                    onResult(null)
                }
        }
    }

    fun crearRegion(nombre: String) {
        viewModelScope.launch {
            if (nombre.isBlank()) return@launch setError("El nombre de la región es obligatorio")

            _uiState.value = _uiState.value.copy(cargando = true, error = null)

            repo.crear(Region(nombre = nombre))
                .onSuccess { creada ->
                    // Actualización optimista
                    val actual = _uiState.value.regiones.toMutableList()
                    actual.add(creada)
                    _uiState.value = _uiState.value.copy(
                        regiones = actual,
                        cargando = false,
                        error = null
                    )
                    _events.emit(RegionEvent.Creada)
                }
                .onFailure { e ->
                    _uiState.value = _uiState.value.copy(
                        cargando = false,
                        error = e.message ?: "Error al crear región"
                    )
                }
        }
    }


    fun actualizarRegion(id: Int, nombre: String) {
        viewModelScope.launch {
            when {
                id <= 0 -> return@launch setError("ID inválido para actualizar")
                nombre.isBlank() -> return@launch setError("El nombre de la región es obligatorio")
            }
            _uiState.value = _uiState.value.copy(cargando = true, error = null)

            repo.actualizar(id, Region(id = id, nombre = nombre))
                .onSuccess { actualizada ->
                    val actual = _uiState.value.regiones.toMutableList()
                    val idx = actual.indexOfFirst { it.id == id }
                    if (idx >= 0) actual[idx] = actualizada
                    _uiState.value = _uiState.value.copy(
                        regiones = actual,
                        cargando = false,
                        error = null
                    )
                    _events.emit(RegionEvent.Actualizada)
                }
                .onFailure { e ->
                    _uiState.value = _uiState.value.copy(
                        cargando = false,
                        error = e.message ?: "Error al actualizar región"
                    )
                }
        }
    }

    fun eliminarRegion(id: Int) {
        viewModelScope.launch {
            if (id <= 0) return@launch setError("ID inválido para eliminar")
            _uiState.value = _uiState.value.copy(cargando = true, error = null)

            repo.eliminar(id)
                .onSuccess {
                    val actual = _uiState.value.regiones.filterNot { it.id == id }
                    _uiState.value = _uiState.value.copy(
                        regiones = actual,
                        cargando = false,
                        error = null
                    )
                    _events.emit(RegionEvent.Eliminada)
                }
                .onFailure { e ->
                    _uiState.value = _uiState.value.copy(
                        cargando = false,
                        error = e.message ?: "Error al eliminar región"
                    )
                }
        }
    }
}
