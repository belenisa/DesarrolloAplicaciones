package com.example.evaluacion2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.evaluacion2.Data.CatalogoCDs
import com.example.evaluacion2.ui.theme.Evaluacion2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Evaluacion2Theme {
                MainComposable()
            }
        }
    }
}


@Composable
fun MainComposable() {
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
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Image(
                        painter = painterResource(id = cd.imageResId),
                        contentDescription = cd.titulo,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Título: ${cd.titulo}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(text = "Artista: ${cd.autor}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Año: ${cd.anio}", style = MaterialTheme.typography.bodySmall)
                    Text(text = "Género: ${cd.genero}", style = MaterialTheme.typography.bodySmall)
                    Text(
                        text = "Precio: \$${cd.precio}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}
