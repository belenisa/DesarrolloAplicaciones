package com.example.evaluacion2.ui.Pantalla


import android.R
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import java.text.DecimalFormat




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaProductos(vm: VerFormularioCD) {
    var autor by remember { mutableStateOf("") }
    var titulo by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var anio by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var imagenUri by remember { mutableStateOf<Uri?>(null) }

    val productos = vm.productos.collectAsState()
    val df = remember { DecimalFormat("#,##0.##") }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imagenUri = uri
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("CDs (Room + Compose)") }) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                OutlinedTextField(value = autor, onValueChange = { autor = it }, label = { Text("Autor") })
                OutlinedTextField(value = titulo, onValueChange = { titulo = it }, label = { Text("Título") })
                OutlinedTextField(
                    value = anio,
                    onValueChange = { anio = it },
                    label = { Text("Año") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
                OutlinedTextField(value = genero, onValueChange = { genero = it }, label = { Text("Género") })
                OutlinedTextField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = { Text("Precio (ej: 19990)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                Button(onClick = { launcher.launch("image/*") }) {
                    Text("Seleccionar imagen")
                }

                imagenUri?.let {
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = "Imagen seleccionada",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Button(onClick = {
                    val precioNum = precio.toIntOrNull()
                    val anioNum = anio.toIntOrNull()
                    if (
                        autor.isNotBlank() &&
                        titulo.isNotBlank() &&
                        precioNum != null &&
                        anioNum != null &&
                        genero.isNotBlank()
                    ) {
                        vm.agregarCD(
                            autor = autor,
                            titulo = titulo,
                            precio = precioNum,
                            anio = anioNum,
                            genero = genero,
                            imagenUri = imagenUri?.toString(),
                            imagenResId = if (imagenUri == null) R.drawable.ic_menu_gallery else null
                        )
                        autor = ""
                        titulo = ""
                        precio = ""
                        anio = ""
                        genero = ""
                        imagenUri = null
                    }
                }) {
                    Text("Agregar CD")
                }

                Divider()
            }

            items(productos.value) { cd ->
                val painter = when {
                    cd.imagenUri?.isNotBlank() == true -> rememberAsyncImagePainter(cd.imagenUri)
                    cd.imagenResId != null -> painterResource(id = cd.imagenResId)
                    else -> painterResource(id = R.drawable.ic_menu_gallery)
                }

                ListItem(
                    leadingContent = {
                        Image(
                            painter = painter,
                            contentDescription = "Imagen del CD",
                            modifier = Modifier
                                .size(64.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    },
                    headlineContent = { Text("${cd.titulo} — \$${df.format(cd.precio)}") },
                    supportingContent = { Text("Autor: ${cd.autor} | Género: ${cd.genero}") },
                    trailingContent = { Text("#${cd.id}") }
                )
                Divider()
            }
        }
    }
}