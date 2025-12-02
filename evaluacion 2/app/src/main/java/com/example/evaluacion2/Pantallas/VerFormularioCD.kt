
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.evaluacion2.Data.Modelo.Producto
import com.example.evaluacion2.viewmodel.ProductoView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerFormularioCD(
    viewModel: ProductoView,
    productoActual: Producto? = null,
    tipoUsuarioActual: String?, // ✅ Nuevo parámetro para validar rol
    onVolver: () -> Unit
) {
    // ✅ Validación de acceso
    if (tipoUsuarioActual != "ADMIN") {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Acceso denegado",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Red
            )
            Spacer(Modifier.height(8.dp))
            Text("Solo administradores pueden crear o editar productos.")
            Spacer(Modifier.height(16.dp))
            Button(onClick = onVolver) {
                Text("Volver")
            }
        }
        return
    }

    var nombre by remember { mutableStateOf(productoActual?.nombre ?: "") }
    var descripcion by remember { mutableStateOf(productoActual?.descripcion ?: "") }
    var precio by remember { mutableStateOf(productoActual?.precio?.toString() ?: "") }
    var stock by remember { mutableStateOf(productoActual?.stock?.toString() ?: "") }
    var imagenUrl by remember {
        mutableStateOf(productoActual?.id?.let { "https://ragemusicbackend.onrender.com/api/imagenes/$it" } ?: "")
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = if (productoActual == null) "Crear Producto" else "Editar Producto",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Yellow
        )
        Spacer(Modifier.height(16.dp))

        if (imagenUrl.isNotBlank()) {
            val painter = rememberAsyncImagePainter(model = imagenUrl)
            Image(
                painter = painter,
                contentDescription = nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .border(2.dp, Color.Yellow, shape = RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.height(8.dp))
        }

        OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = precio, onValueChange = { precio = it }, label = { Text("Precio") })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = stock, onValueChange = { stock = it }, label = { Text("Stock") })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = imagenUrl, onValueChange = { imagenUrl = it }, label = { Text("URL Imagen") })

        Spacer(Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                val precioDouble = precio.toDoubleOrNull() ?: 0.0
                val stockInt = stock.toIntOrNull() ?: 0

                if (nombre.isBlank() || descripcion.isBlank()) {
                    return@Button
                }

                if (productoActual == null) {
                    viewModel.crearProducto(nombre, descripcion, precioDouble, stockInt)
                } else {
                    viewModel.actualizarProducto(productoActual.id ?: 0, nombre, descripcion, precioDouble, stockInt)
                }
                onVolver()
            }, colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)) {
                Text("Guardar", color = Color.Black)
            }

            Button(onClick = onVolver) {
                Text("Cancelar")
            }
        }
    }
}
