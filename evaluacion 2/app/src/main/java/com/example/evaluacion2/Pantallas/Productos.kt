
package com.example.evaluacion2.ui.Pantalla

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.evaluacion2.Data.Modelo.Producto
import com.example.evaluacion2.Modelo.GuardarCompras
import com.example.evaluacion2.viewmodel.ProductoView


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Producto(
    viewModel: ProductoView,
    onProductoClick: (Producto) -> Unit,
    guardarCompras: GuardarCompras,
    navController: NavController,
    tipoUsuarioActual: String?
) {
    val productos by viewModel.productos.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        // barra de estado
        if (cargando) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
        }
        error?.let {
            Text("Error: $it", color = MaterialTheme.colorScheme.error)
            Spacer(Modifier.height(8.dp))
        }

        // Lista
        productos.forEach { producto ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onProductoClick(producto) },
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    // Imagen desde backend: usa producto.imagen?.id si lo tienes
                    val imagenId = producto.imagen?.id ?: producto.id
                    val imagenUrl = imagenId?.let { "https://ragemusicbackend.onrender.com/api/imagenes/$it" }
                    val painter = rememberAsyncImagePainter(model = imagenUrl)

                    if (imagenUrl != null) {
                        androidx.compose.foundation.Image(
                            painter = painter,
                            contentDescription = producto.nombre,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .padding(bottom = 8.dp)
                                .border(2.dp, Color.Yellow, shape = RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Text("Nombre: ${producto.nombre}", style = MaterialTheme.typography.titleMedium, color = Color.Yellow)
                    Text("Descripción: ${producto.descripcion}", style = MaterialTheme.typography.bodyMedium, color = Color.Yellow)
                    Text("Precio: \$${producto.precio}", style = MaterialTheme.typography.bodySmall, color = Color.Yellow, fontWeight = FontWeight.SemiBold)
                    Text("Stock: ${producto.stock}", style = MaterialTheme.typography.bodySmall, color = Color.Yellow)

                    producto.tipoProducto?.let {
                        Text("Tipo: ${it.nombre}", style = MaterialTheme.typography.bodySmall, color = Color.Yellow)
                    }
                    producto.artistas?.artista?.let {
                        Text("Artista: ${it.nombre}", style = MaterialTheme.typography.bodySmall, color = Color.Yellow)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Comprar: agrega al carrito con cantidad inicial = 1
                        Button(
                            onClick = { guardarCompras.agregarProducto(producto) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)
                        ) {
                            Text("Comprar", color = Color.Black)
                        }

                        // Eliminar solo para Admin
                        if (tipoUsuarioActual == "Admin") {
                            IconButton(onClick = { producto.id?.let(viewModel::eliminarProducto) }) {
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

        // Pie: acciones
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { viewModel.cargarProductos() }) { Text("Recargar") }
            Button(onClick = { navController.navigate("verCarrito") }) { Text("Ver carrito") }
        }
    }
}
