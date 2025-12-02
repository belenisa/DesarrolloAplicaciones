
package com.example.evaluacion2

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.evaluacion2.ui.AppNavigation
import com.example.evaluacion2.ui.theme.Evaluacion2Theme
import com.example.evaluacion2.viewmodel.AuthViewModel
import com.example.evaluacion2.viewmodel.AuthViewModelFactory
import com.example.evaluacion2.viewmodel.ProductoView
import com.example.evaluacion2.repositorio.AuthRepositorio


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Evaluacion2Theme {
                val productoView: ProductoView = viewModel()

                val repo = AuthRepositorio(ApiNet.usuarioService)
                val factory = AuthViewModelFactory(repo)
                val authViewModel: AuthViewModel = viewModel(factory = factory)

                AppNavigation(
                    productoView = productoView,
                    authViewModel = authViewModel
                )
            }
        }
    }
}
