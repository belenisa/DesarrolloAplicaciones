package com.example.evaluacion2.Pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.evaluacion2.Data.CatalogoCDs
import com.example.evaluacion2.Data.Comprar
import com.example.evaluacion2.Modelo.CD
import com.example.evaluacion2.Modelo.GuardarCompras

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Producto(onCDClick: (CD) -> Unit, guardarCompras: GuardarCompras) {
    val catalogo = CatalogoCDs()
    val cds = catalogo.mostrarCDs()

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
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Image(
                        painter = painterResource(id = cd.imageResId),
                        contentDescription = cd.titulo,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                            .border(2.dp, Color.Yellow)
                    )
                    Text(
                        text = "Título: ${cd.titulo}",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Yellow
                    )
                    Text(
                        text = "Artista: ${cd.autor}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Yellow
                    )
                    Text(
                        text = "Año: ${cd.anio}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Yellow
                    )
                    Text(
                        text = "Género: ${cd.genero}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Yellow
                    )
                    Text(
                        text = "Precio: \$${cd.precio}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Yellow
                    )
                    Comprar(cd = cd, guardarCompras = guardarCompras)
                }
            }
        }
    }
}
