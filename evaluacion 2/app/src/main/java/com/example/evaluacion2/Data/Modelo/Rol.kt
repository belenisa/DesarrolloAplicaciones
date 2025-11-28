package com.example.evaluacion2.Data.Modelo

// Enum que refleja exactamente los valores del backend (EnumType.STRING)
enum class RolOpc {
    ADMIN, CLIENTE, GERENTE, VENTAS, LOGISTICA, PROVEEDOR
}

data class Rol(
    val id: Int? = null,
    val rol: RolOpc
)