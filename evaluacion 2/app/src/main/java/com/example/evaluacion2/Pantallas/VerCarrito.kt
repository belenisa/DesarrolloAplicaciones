
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.evaluacion2.Modelo.GuardarCompras

@Composable
fun VerCarrito(guardarCompras: GuardarCompras) {
    val carrito = guardarCompras.carrito

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
                carrito.forEach { producto ->
                    val cantidad = guardarCompras.cantidades[producto.nombre] ?: 1
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "- ${producto.nombre} (\$${producto.precio})",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Yellow
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Button(
                                onClick = { guardarCompras.decrementarCantidad(producto) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier.size(30.dp)
                            ) {
                                Text("-", color = Color.Black, fontSize = 18.sp)
                            }

                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .border(2.dp, Color.Yellow, shape = RoundedCornerShape(8.dp))
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Text("$cantidad", color = Color.White, fontSize = 20.sp)
                            }

                            Button(
                                onClick = { guardarCompras.incrementarCantidad(producto) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier.size(30.dp)
                            ) {
                                Text("+", color = Color.Black, fontSize = 18.sp)
                            }

                            IconButton(
                                onClick = { guardarCompras.quitarProducto(producto) },
                                modifier = Modifier.size(40.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
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

        Text(
            "Total: \$${guardarCompras.calcularTotal()}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Aquí irá la lógica para finalizar compra
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text("Finalizar compra", color = Color.Yellow)
        }
    }
}
