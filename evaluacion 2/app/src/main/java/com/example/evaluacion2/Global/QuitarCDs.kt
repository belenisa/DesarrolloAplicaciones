package com.example.evaluacion2.Global

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.evaluacion2.Modelo.CD
import com.example.evaluacion2.ui.Pantalla.VerFormularioCD

@Composable
fun QuitarCDs(cd: CD, viewModel: VerFormularioCD, tipoUsuarioActual: String) {
    if (tipoUsuarioActual == "Admin") {
        IconButton(
            onClick = { viewModel.eliminarCD(cd) },
            modifier = Modifier.size(50.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Eliminar CD",
                tint = Color.White
            )
        }
    }
}