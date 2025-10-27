package com.example.evaluacion2.Pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.evaluacion2.Modelo.GuardarCompras
import java.nio.file.WatchEvent

@Composable
fun VerCarrito(guardarCompras: GuardarCompras) {
    val carrito = guardarCompras.carrito
    val total = carrito.sumOf { it.precio }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Carrito de compras", style = MaterialTheme.typography.titleLarge)

        Card(modifier = Modifier
            .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.Black
            )
        ) {
            if (carrito.isEmpty()) {
                Text("Tu carrito está vacío.", style = MaterialTheme.typography.bodyLarge)
            } else {
                carrito.forEach { cd ->
                    Text(
                        "- ${cd.titulo} (\$${cd.precio})",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Yellow
                    )
                }

            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Total: \$${total}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

    }
}