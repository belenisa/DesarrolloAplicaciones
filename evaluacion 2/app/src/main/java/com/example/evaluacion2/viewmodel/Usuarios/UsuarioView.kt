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

class UsuarioView (
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
                repo.listar()
                    .onSuccess { _usuarios.value = it; _error.value = null }
                    .onFailure { _error.value = it.message }
                _cargando.value = false
            }
        }

        fun crearUsuario(nombre: String, correo: String, Contrasena: String, rol: RolOpc) {
            viewModelScope.launch {
                val nuevo = Usuarios(
                    nombre = nombre,
                    correo = correo,
                    Contrasena = Contrasena,
                    rol = Rol(id = null, rol = rol)
                )
                repo.crear(nuevo)
                    .onSuccess { cargarUsuarios() }
                    .onFailure { _error.value = it.message }
            }
        }

        fun actualizarUsuario(id: Int, nombre: String, correo: String, rol: RolOpc) {
            viewModelScope.launch {
                val datos = Usuarios(
                    id = id,
                    nombre = nombre,
                    correo = correo,
                    rol = Rol(id = null, rol = rol)
                )
                repo.actualizar(id, datos)
                    .onSuccess { cargarUsuarios() }
                    .onFailure { _error.value = it.message }
            }
        }

        fun eliminarUsuario(id: Int) {
            viewModelScope.launch {
                repo.eliminar(id)
                    .onSuccess { cargarUsuarios() }
                    .onFailure { _error.value = it.message }
            }
        }
}
