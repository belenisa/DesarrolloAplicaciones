package com.example.evaluacion2


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.lifecycle.ViewModelProvider
import com.example.evaluacion2.Modelo.DataCD


import com.example.evaluacion2.ui.AppNavigation

import com.example.evaluacion2.ui.Pantallaformulario.VerFormularioCD
import com.example.evaluacion2.ui.Pantallaformulario.VerFormularioCDFactory
import com.example.evaluacion2.ui.theme.Evaluacion2Theme


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Obtener instancia del DAO y ViewModel usando el Factory
        val dao = DataCD.getInstance(this).CDDao()
        val factory = VerFormularioCDFactory(dao)
        val viewModel = ViewModelProvider(this, factory)[VerFormularioCD::class.java]

        // Verificar si ya se insertó el catálogo
        val prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val yaInsertado = prefs.getBoolean("catalogo_insertado", false)

        if (!yaInsertado) {
            viewModel.insertarCatalogoInicial()
            prefs.edit().putBoolean("catalogo_insertado", true).apply()
        }

        setContent {
            Evaluacion2Theme {
                AppNavigation(viewModel = viewModel) // ✅ Se pasa el ViewModel
            }

        }
    }
}