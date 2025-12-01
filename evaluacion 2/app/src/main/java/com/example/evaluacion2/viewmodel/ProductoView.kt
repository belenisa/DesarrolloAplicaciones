
package com.example.evaluacion2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaluacion2.Data.Modelo.Producto
import com.example.evaluacion2.Data.Modelo.TipoProducto
import com.example.evaluacion2.Data.Modelo.Artistas
import com.example.evaluacion2.repositorio.ProductoRepositorio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductoView(
    private val repo: ProductoRepositorio = ProductoRepositorio()
) : ViewModel() {

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // --- Listar ---
    fun cargarProductos() {
        viewModelScope.launch {
            _cargando.value = true
            try {
                repo.listar()
                    .onSuccess { lista ->
                        _productos.value = lista
                        _error.value = null
                    }
                    .onFailure { e ->
                        _error.value = e.message ?: "No se pudieron cargar los productos"
                    }
            } finally {
                _cargando.value = false
            }
        }
    }

    // --- Obtener (opcional) ---
    fun obtenerProducto(id: Int, onResult: (Producto?) -> Unit) {
        viewModelScope.launch {
            if (id <= 0) {
                _error.value = "ID inválido para obtener producto"
                onResult(null)
                return@launch
            }
            repo.obtener(id)
                .onSuccess { producto -> onResult(producto) }
                .onFailure { e ->
                    _error.value = e.message ?: "Error al obtener producto"
                    onResult(null)
                }
        }
    }

    // --- Crear ---
    fun crearProducto(
        nombre: String,
        descripcion: String,
        precio: Double,
        stock: Int,
        tipoProducto: TipoProducto? = null,
        artistas: Artistas? = null
    ) {
        viewModelScope.launch {
            // Validaciones mínimas
            when {
                nombre.isBlank() -> {
                    _error.value = "El nombre del producto es obligatorio"; return@launch
                }
                descripcion.isBlank() -> {
                    _error.value = "La descripción es obligatoria"; return@launch
                }
                precio < 0.0 -> {
                    _error.value = "El precio no puede ser negativo"; return@launch
                }
                stock < 0 -> {
                    _error.value = "El stock no puede ser negativo"; return@launch
                }
            }

            _cargando.value = true
            try {
                val nuevo = Producto(
                    nombre = nombre,
                    descripcion = descripcion,
                    precio = precio,
                    stock = stock,
                    tipoProducto = tipoProducto,
                    artistas = artistas // o null si N:N
                )

                repo.crear(nuevo)
                    .onSuccess { creado ->
                        _productos.value = _productos.value + creado
                        _error.value = null
                    }
                    .onFailure { e ->
                        _error.value = e.message ?: "Error al crear producto"
                    }
            } finally {
                _cargando.value = false
            }
        }
    }

    // --- Actualizar ---
    fun actualizarProducto(
        id: Int,
        nombre: String,
        descripcion: String,
        precio: Double,
        stock: Int,
        tipoProducto: TipoProducto? = null,
        artistas: Artistas? = null
    ) {
        viewModelScope.launch {
            if (id <= 0) {
                _error.value = "ID inválido para actualizar"; return@launch
            }
            when {
                nombre.isBlank() -> {
                    _error.value = "El nombre del producto es obligatorio"; return@launch
                }
                descripcion.isBlank() -> {
                    _error.value = "La descripción es obligatoria"; return@launch
                }
                precio < 0.0 -> {
                    _error.value = "El precio no puede ser negativo"; return@launch
                }
                stock < 0 -> {
                    _error.value = "El stock no puede ser negativo"; return@launch
                }
            }

            _cargando.value = true
            try {
                val datos = Producto(
                    id = id,
                    nombre = nombre,
                    descripcion = descripcion,
                    precio = precio,
                    stock = stock,
                    tipoProducto = tipoProducto,
                    artistas = artistas
                )

                repo.actualizar(id, datos)
                    .onSuccess { actualizado ->
                        val lista = _productos.value.toMutableList()
                        val idx = lista.indexOfFirst { it.id == id }
                        if (idx >= 0) lista[idx] = actualizado
                        _productos.value = lista
                        _error.value = null
                    }
                    .onFailure { e ->
                        _error.value = e.message ?: "Error al actualizar producto"
                    }
            } finally {
                _cargando.value = false
            }
        }
    }

    // --- Eliminar ---
    fun eliminarProducto(id: Int) {
        viewModelScope.launch {
            if (id <= 0) {
                _error.value = "ID inválido para eliminar"; return@launch
            }
            _cargando.value = true
            try {
                repo.eliminar(id)
                    .onSuccess {
                        _productos.value = _productos.value.filterNot { it.id == id }
                        _error.value = null
                    }
                    .onFailure { e ->
                        _error.value = e.message ?: "Error al eliminar producto"
                    }
            } finally {
                _cargando.value = false
            }
        }
    }
}
