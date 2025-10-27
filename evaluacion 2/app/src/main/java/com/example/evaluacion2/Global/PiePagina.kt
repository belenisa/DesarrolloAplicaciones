package com.example.evaluacion2.Global

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun PieDePagina(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.Black)
            .padding(15.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Inicio(navController)
        Productos(navController)
        Carrito(navController)
        Text("Contacto", color = Yellow, fontWeight = FontWeight.Bold)
    }
}


@Composable
fun Inicio(navController: NavController) {
    Button(
        onClick = { navController.navigate("main") },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
    ) {
        Text("🏠", color = Color.Yellow)
    }
}

@Composable
fun Productos (navController: NavController) {
    Button(
        onClick = { navController.navigate("producto") },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
    ) {
        Text("CDs", color = Color.Yellow)
    }
}

@Composable
fun Carrito (navController: NavController) {
    Button(
        onClick = { navController.navigate("carrito") },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
    ) {
        Text("\uD83D\uDED2", color = Color.Yellow)
    }
}
