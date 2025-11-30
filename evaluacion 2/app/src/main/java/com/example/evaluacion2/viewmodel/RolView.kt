
package com.example.evaluacion2.viewmodel.Rol

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaluacion2.Data.Modelo.Rol
import com.example.evaluacion2.repositorio.RolRepositorio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RolViewModel(
    private val repo: RolRepositorio = RolRepositorio()
) : ViewModel() {

    private val _roles = MutableStateFlow<List<Rol>>(emptyList())
    val roles: StateFlow<List<Rol>> = _roles

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun cargarRoles() {
        viewModelScope.launch {
            _cargando.value = true
            try {
                val lista = repo.listarRoles()
                if (lista != null) {
                    _roles.value = lista
                    _error.value = null
                } else {
                    _error.value = "No se pudieron cargar los roles"
                }

            } finally {
                _cargando.value = false
            }
        }
    }
}
