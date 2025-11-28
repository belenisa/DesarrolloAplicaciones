package com.example.evaluacion2.repositorio

import com.example.evaluacion2.Data.network.Rol.RolApi
import com.example.evaluacion2.Data.network.Rol.RolService

class RolRepositorio(
    private val service: RolService = RolApi.retrofit.create(RolService::class.java)
)
{
}