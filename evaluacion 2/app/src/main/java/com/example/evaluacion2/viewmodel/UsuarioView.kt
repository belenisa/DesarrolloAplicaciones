
package com.example.evaluacion2.viewmodel.Usuarios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaluacion2.Data.Modelo.Rol
import com.example.evaluacion2.Data.Modelo.RolOpc
import com.example.evaluacion2.Modelo.Usuarios
import com.example.evaluacion2.repositorio.UsuarioRepositorio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsuarioView(
    private val repo: UsuarioRepositorio = UsuarioRepositorio()
) : ViewModel() {

    private val _usuarios = MutableStateFlow<List<Usuarios>>(emptyList())
    val usuarios: StateFlow<List<Usuarios>> = _usuarios

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun cargarUsuarios() {
        viewModelScope.launch {
            _cargando.value = true
            try {
                repo.listar()
                    .onSuccess { list ->
                        _usuarios.value = list
                        _error.value = null
                    }
                    .onFailure { e ->
                        _error.value = e.message ?: "Error desconocido al cargar usuarios"
                    }
            } finally {
                _cargando.value = false
            }
        }
    }

    fun crearUsuario(nombre: String, correo: String, contrasena: String, rol: RolOpc) {
        viewModelScope.launch {
            if (nombre.isBlank() || correo.isBlank() || contrasena.isBlank()) {
                _error.value = "Nombre, correo y contraseña son obligatorios"
                return@launch
            }

            val nuevo = Usuarios(
                nombre = nombre,
                correo = correo,
                contrasena = contrasena,
                rol = Rol(id = null, rol = rol)
            )

            repo.crear(nuevo)
                .onSuccess {
                    _error.value = null
                    cargarUsuarios()
                }
                .onFailure { e ->
                    _error.value = e.message ?: "Error al crear usuario"
                }
        }
    }

    fun actualizarUsuario(id: Int, nombre: String, correo: String, rol: RolOpc) {
        viewModelScope.launch {
            if (id <= 0) {
                _error.value = "ID inválido para actualizar"
                return@launch
            }
            if (nombre.isBlank() || correo.isBlank()) {
                _error.value = "Nombre y correo son obligatorios"
                return@launch
            }

            val datos = Usuarios(
                id = id,
                nombre = nombre,
                correo = correo,
                rol = Rol(id = null, rol = rol)
            )

            repo.actualizar(id, datos)
                .onSuccess {
                    _error.value = null
                    cargarUsuarios()
                }
                .onFailure { e ->
                    _error.value = e.message ?: "Error al actualizar usuario"
                }
        }
    }

    fun eliminarUsuario(id: Int) {
        viewModelScope.launch {
            if (id <= 0) {
                _error.value = "ID inválido para eliminar"
                return@launch
            }
            repo.eliminar(id)
                .onSuccess {
                    _error.value = null
                    cargarUsuarios()
                }
                .onFailure { e ->
                    _error.value = e.message ?: "Error al eliminar usuario"
                }
        }
    }
}
