package com.example.evaluacion2.Pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.evaluacion2.Data.Comprar
import com.example.evaluacion2.Modelo.CD
import com.example.evaluacion2.Modelo.GuardarCompras
import com.example.evaluacion2.ui.Pantalla.VerFormularioCD




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Producto(
    viewModel: VerFormularioCD,
    onCDClick: (CD) -> Unit,
    guardarCompras: GuardarCompras,
    navController: NavController
) {
    val cds by viewModel.productos.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(cds) { cd ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onCDClick(cd) },
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {


                    val painter = if (cd.imagenResId == null) {
                        rememberAsyncImagePainter(model = cd.imagenUri)
                    } else {
                        painterResource(id = cd.imagenResId)
                    }

                    Image(
                        painter = painter,
                        contentDescription = cd.titulo,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .padding(bottom = 8.dp)
                            .border(2.dp, Color.Yellow)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Text("Título: ${cd.titulo}", style = MaterialTheme.typography.titleMedium, color = Color.Yellow)
                    Text("Artista: ${cd.autor}", style = MaterialTheme.typography.bodyMedium, color = Color.Yellow)
                    Text("Año: ${cd.anio}", style = MaterialTheme.typography.bodySmall, color = Color.Yellow)
                    Text("Género: ${cd.genero}", style = MaterialTheme.typography.bodySmall, color = Color.Yellow)
                    Text("Precio: \$${cd.precio}", style = MaterialTheme.typography.bodySmall, color = Color.Yellow)

                    Comprar(cd = cd, guardarCompras = guardarCompras)
                }
            }
        }
    }
}

