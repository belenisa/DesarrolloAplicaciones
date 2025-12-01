package com.example.evaluacion2.ui.Pantalla



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.evaluacion2.viewmodel.ProductoView
import java.text.DecimalFormat





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaProductos(vm: ProductoView) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }

    val productos by vm.productos.collectAsState()
    val cargando by vm.cargando.collectAsState()
    val error by vm.error.collectAsState()

    val df = remember { DecimalFormat("#,##0.##") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Gestión de Productos") }) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                if (cargando) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
                error?.let { err ->
                    Text("Error: $err", color = MaterialTheme.colorScheme.error)
                }

                OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
                OutlinedTextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") })
                OutlinedTextField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = { Text("Precio") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
                OutlinedTextField(
                    value = stock,
                    onValueChange = { stock = it },
                    label = { Text("Stock") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                Button(onClick = {
                    val precioNum = precio.toDoubleOrNull()
                    val stockNum = stock.toIntOrNull()
                    if (nombre.isNotBlank() && descripcion.isNotBlank() && precioNum != null && stockNum != null) {
                        vm.crearProducto(nombre, descripcion, precioNum, stockNum)
                        nombre = ""; descripcion = ""; precio = ""; stock = ""
                    }
                }) {
                    Text("Agregar Producto")
                }

                Button(onClick = { vm.cargarProductos() }) { Text("Recargar") }

                Divider()
            }

            items(productos) { producto ->
                ListItem(
                    headlineContent = { Text("${producto.nombre} — \$${df.format(producto.precio)}") },
                    supportingContent = { Text("Stock: ${producto.stock} | ${producto.descripcion}") },
                    trailingContent = {
                        Column {
                            Text("#${producto.id}")
                            Spacer(Modifier.height(4.dp))
                            OutlinedButton(onClick = { producto.id?.let { vm.eliminarProducto(it) } }) {
                                Text("Eliminar")
                            }
                        }
                    }
                )
                Divider()
            }
        }
    }
}
