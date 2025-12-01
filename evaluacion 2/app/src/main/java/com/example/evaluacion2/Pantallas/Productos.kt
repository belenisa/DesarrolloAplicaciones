import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.evaluacion2.Data.Modelo.GuardarCompras
import com.example.evaluacion2.Data.Modelo.Producto
import com.example.evaluacion2.viewmodel.ProductoView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Producto(
    viewModel: ProductoView, // Usamos tu ViewModel actual
    onCDClick: (Producto) -> Unit, // Mantengo el nombre del callback
    guardarCompras: GuardarCompras,
    navController: NavController,
    tipoUsuarioActual: String?
) {
    val productos by viewModel.productos.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(productos) { producto ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onCDClick(producto) },
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    // Imagen desde API
                    val imagenUrl = "https://ragemusicbackend.onrender.com/api/imagenes/${producto.id}"
                    val painter = rememberAsyncImagePainter(model = imagenUrl)

                    Image(
                        painter = painter,
                        contentDescription = producto.nombre,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .padding(bottom = 8.dp)
                            .border(2.dp, Color.Yellow)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )

                    // Datos del producto
                    Text("Nombre: ${producto.nombre}", style = MaterialTheme.typography.titleMedium, color = Color.Yellow)
                    Text("Descripción: ${producto.descripcion}", style = MaterialTheme.typography.bodyMedium, color = Color.Yellow)
                    Text("Precio: \$${producto.precio}", style = MaterialTheme.typography.bodySmall, color = Color.Yellow)
                    Text("Stock: ${producto.stock}", style = MaterialTheme.typography.bodySmall, color = Color.Yellow)

                    producto.tipoProducto?.let {
                        Text("Tipo: ${it.nombre}", style = MaterialTheme.typography.bodySmall, color = Color.Yellow)
                    }

                    producto.artistas?.artista?.let {
                        Text("Artista: ${it.nombre}", style = MaterialTheme.typography.bodySmall, color = Color.Yellow)
                    }

                    // Botones
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Comprar
                        Comprar(cd = producto, guardarCompras = guardarCompras) // Mantengo el nombre del parámetro "cd"

                        // Eliminar solo para Admin
                        if (tipoUsuarioActual == "Admin") {
                            QuitarCDs(cd = producto, viewModel = viewModel, tipoUsuarioActual = tipoUsuarioActual)
                        }
                    }
                }
            }
        }
    }
}

private fun ColumnScope.rememberAsyncImagePainter(model: String) {}
