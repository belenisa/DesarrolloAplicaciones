
package com.example.evaluacion2.ui.Pantalla

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

        // Barra de carga
        if (cargando) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
        }

        // Mostrar error si existe
        error?.let {
            Text("Error: $it", color = MaterialTheme.colorScheme.error)
            Spacer(Modifier.height(8.dp))
        }

        // Lista de productos con LazyColumn
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(productos) { producto ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onProductoClick(producto) },
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Black)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        val imagenUrl = producto.id?.let { "https://ragemusicbackend.onrender.com/api/imagenes/$it" }
                        val painter = rememberAsyncImagePainter(model = imagenUrl)

                        if (imagenUrl != null) {
                            Image(
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

                        // Información del producto
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

                        // Botones de acción
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = { guardarCompras.agregarProducto(producto) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)
                            ) {
                                Text("Comprar", color = Color.Black)
                            }

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
        }

        // Pie con acciones
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { viewModel.cargarProductos() }) { Text("Recargar") }
            Button(onClick = { navController.navigate("verCarrito") }) { Text("Ver carrito") }
        }
    }
}
