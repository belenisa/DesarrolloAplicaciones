package com.example.evaluacion2.ui.Pantalla

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.evaluacion2.Data.Modelo.Producto
import com.example.evaluacion2.Modelo.GuardarCompras
import com.example.evaluacion2.viewmodel.ProductoView


@Composable
fun IndividualCD
            (
    producto: Producto,
    guardarCompras: GuardarCompras,
    viewModel: ProductoView,
    tipoUsuarioActual: String?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Column {
            // Imagen desde backend usando id del producto
            val imagenUrl = producto.id?.let { "https://ragemusicbackend.onrender.com/api/imagenes/$it" }
            val painter = rememberAsyncImagePainter(model = imagenUrl)

            Image(
                painter = painter,
                contentDescription = producto.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .padding(bottom = 8.dp),
                contentScale = ContentScale.Crop
            )

            // Información del producto
            Text("Nombre: ${producto.nombre}", style = MaterialTheme.typography.titleLarge, color = Color.Yellow)
            Text("Descripción: ${producto.descripcion}", style = MaterialTheme.typography.bodyLarge, color = Color.Yellow)
            Text("Precio: \$${producto.precio}", style = MaterialTheme.typography.bodyMedium, color = Color.Yellow)
            Text("Stock: ${producto.stock}", style = MaterialTheme.typography.bodyMedium, color = Color.Yellow)

            producto.tipoProducto?.let {
                Text("Tipo: ${it.nombre}", style = MaterialTheme.typography.bodyMedium, color = Color.Yellow)
            }
            producto.artistas?.artista?.let {
                Text("Artista: ${it.nombre}", style = MaterialTheme.typography.bodyMedium, color = Color.Yellow)
            }

            // Botones
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón comprar
                Button(
                    onClick = { guardarCompras.agregarProducto(producto) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)
                ) {
                    Text("Comprar", color = Color.Black)
                }

                // Botón eliminar solo para Admin
                if (tipoUsuarioActual == "ADMIN") {
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