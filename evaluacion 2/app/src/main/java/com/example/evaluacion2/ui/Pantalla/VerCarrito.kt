package com.example.evaluacion2.ui.Pantalla

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.evaluacion2.Modelo.GuardarCompras

@Composable
fun VerCarrito(guardarCompras: GuardarCompras) {
    val carrito = guardarCompras.carrito
    val cantidades = guardarCompras.cantidades

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Carrito de compras", style = MaterialTheme.typography.titleLarge)

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.Black)
        ) {
            if (carrito.isEmpty()) {
                Text("Tu carrito está vacío.", style = MaterialTheme.typography.bodyLarge)
            } else {
                carrito.forEach { cd ->
                    val cantidad = cantidades[cd.titulo] ?: 1
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "- ${cd.titulo} (\$${cd.precio})",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Yellow
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Botón MENOS
                            Button(
                                onClick = { if (cantidad > 1) cantidades[cd.titulo] = cantidad - 1 },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier.size(30.dp)
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("-", color = Color.Black, fontSize = 18.sp)
                                }
                            }

                            // Cantidad con borde amarillo
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .border(2.dp, Color.Yellow, shape = RoundedCornerShape(8.dp))
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Text("$cantidad", color = Color.White, fontSize = 20.sp)
                            }

                            // Botón MÁS
                            Button(
                                onClick = { cantidades[cd.titulo] = cantidad + 1 },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier.size(30.dp)
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("+", color = Color.Black, fontSize = 18.sp)
                                }
                            }

                            // Botón ELIMINAR
                            IconButton(
                                onClick = {
                                    guardarCompras.carrito.remove(cd)
                                    guardarCompras.cantidades.remove(cd.titulo)
                                },
                                modifier = Modifier.size(40.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Eliminar",
                                    tint = Color.Yellow
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val total = carrito.sumOf { cd ->
            val cantidad = cantidades[cd.titulo] ?: 1
            cd.precio * cantidad
        }

        Text(
            "Total: \$${total}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}