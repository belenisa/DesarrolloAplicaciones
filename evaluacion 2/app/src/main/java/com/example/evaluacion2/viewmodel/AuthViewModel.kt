package com.example.evaluacion2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaluacion2.Modelo.Usuarios
import com.example.evaluacion2.repositorio.AuthRepositorio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch



class AuthViewModel(private val repo: AuthRepositorio) : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _usuario = MutableStateFlow<Usuarios?>(null)
    val usuario: StateFlow<Usuarios?> = _usuario

    fun login(correo: String, contrasena: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                _error.value = null
                _usuario.value = null

                repo.login(correo, contrasena)
                    .onSuccess { user ->
                        _usuario.value = user
                    }
                    .onFailure { e ->
                        _error.value = e.message ?: "Error desconocido"
                    }
            } finally {
                _loading.value = false
            }
        }
    }

    fun setError(message: String) {
        _error.value = message
    }

    fun clearError() {
        _error.value = null
    }

    fun logout() {
        _usuario.value = null
        _error.value = null
    }
}
