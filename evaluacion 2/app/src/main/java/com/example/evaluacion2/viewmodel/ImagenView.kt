
package com.example.evaluacion2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaluacion2.Data.Modelo.Imagen
import com.example.evaluacion2.Data.Modelo.Producto
import com.example.evaluacion2.repositorio.ImagenRepositorio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ImagenView(
    private val repo: ImagenRepositorio = ImagenRepositorio()
) : ViewModel() {

    private val _imagenes = MutableStateFlow<List<Imagen>>(emptyList())
    val imagenes: StateFlow<List<Imagen>> = _imagenes

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // --- Listar ---
    fun cargarImagenes() {
        viewModelScope.launch {
            _cargando.value = true
            try {
                repo.listar()
                    .onSuccess { lista ->
                        _imagenes.value = lista
                        _error.value = null
                    }
                    .onFailure { e ->
                        _error.value = e.message ?: "No se pudieron cargar las imágenes"
                    }
            } finally {
                _cargando.value = false
            }
        }
    }

    // --- Obtener (opcional) ---
    fun obtenerImagen(id: Int, onResult: (Imagen?) -> Unit) {
        viewModelScope.launch {
            if (id <= 0) {
                _error.value = "ID inválido para obtener imagen"
                onResult(null)
                return@launch
            }
            repo.obtener(id)
                .onSuccess { imagen -> onResult(imagen) }
                .onFailure { e ->
                    _error.value = e.message ?: "Error al obtener imagen"
                    onResult(null)
                }
        }
    }

    // --- Crear ---
    fun crearImagen(nombre: String, productoId: Int?) {
        viewModelScope.launch {
            when {
                nombre.isBlank() -> {
                    _error.value = "El nombre de la imagen es obligatorio"; return@launch
                }
                productoId != null && productoId <= 0 -> {
                    _error.value = "ID de producto inválido"; return@launch
                }
            }

            _cargando.value = true
            try {
                val asociacion = productoId?.let { Producto(id = it, nombre = "", descripcion = "", precio = 0.0, stock = 0) }

                val nueva = Imagen(
                    nombre = nombre,
                    producto = asociacion
                )

                repo.crear(nueva)
                    .onSuccess { creada ->
                        _imagenes.value = _imagenes.value + creada
                        _error.value = null
                    }
                    .onFailure { e ->
                        _error.value = e.message ?: "Error al crear imagen"
                    }
            } finally {
                _cargando.value = false
            }
        }
    }

    // --- Actualizar ---
    fun actualizarImagen(id: Int, nombre: String, productoId: Int?) {
        viewModelScope.launch {
            if (id <= 0) { _error.value = "ID inválido para actualizar"; return@launch }
            if (nombre.isBlank()) { _error.value = "El nombre de la imagen es obligatorio"; return@launch }
            if (productoId != null && productoId <= 0) { _error.value = "ID de producto inválido"; return@launch }

            _cargando.value = true
            try {
                val asociacion = productoId?.let { Producto(id = it, nombre = "", descripcion = "", precio = 0.0, stock = 0) }

                val datos = Imagen(
                    id = id,
                    nombre = nombre,
                    producto = asociacion
                )

                repo.actualizar(id, datos)
                    .onSuccess { actualizada ->
                        val lista = _imagenes.value.toMutableList()
                        val idx = lista.indexOfFirst { it.id == id }
                        if (idx >= 0) lista[idx] = actualizada
                        _imagenes.value = lista
                        _error.value = null
                    }
                    .onFailure { e ->
                        _error.value = e.message ?: "Error al actualizar imagen"
                    }
            } finally {
                _cargando.value = false
            }
        }
    }

    // --- Eliminar ---
    fun eliminarImagen(id: Int) {
        viewModelScope.launch {
            if (id <= 0) { _error.value = "ID inválido para eliminar"; return@launch }
            _cargando.value = true
            try {
                repo.eliminar(id)
                    .onSuccess {
                        _imagenes.value = _imagenes.value.filterNot { it.id == id }
                        _error.value = null
                    }
                    .onFailure { e ->
                        _error.value = e.message ?: "Error al eliminar imagen"
                    }
            } finally {
                _cargando.value = false
            }
        }
    }
}
