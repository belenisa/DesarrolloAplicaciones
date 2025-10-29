package com.example.evaluacion2.ui.Pantalla

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun Contactos() {
    var texto by remember { mutableStateOf("") }
    Encabezado()

}

@Composable
fun Encabezado() {
    Text(
        text = "Contacto",
        color = Color(0xFF000000),
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    )
}