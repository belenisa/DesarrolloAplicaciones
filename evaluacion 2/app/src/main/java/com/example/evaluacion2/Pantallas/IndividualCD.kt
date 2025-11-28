package com.example.evaluacion2.ui.Pantalla

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.evaluacion2.Data.Comprar
import com.example.evaluacion2.Global.QuitarCDs
import com.example.evaluacion2.Modelo.CD
import com.example.evaluacion2.Modelo.GuardarCompras


@Composable
fun IndividualCD(cd: CD, guardarCompras: GuardarCompras, viewModel: VerFormularioCD,tipoUsuarioActual: String?)
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Column {

            val painter = when {
                cd.imagenUri?.isNotBlank() == true -> rememberAsyncImagePainter(cd.imagenUri)
                cd.imagenResId != null -> painterResource(id = cd.imagenResId)
                else -> painterResource(id = R.drawable.ic_menu_gallery) // imagen por defecto
            }

            Image(
                painter = painter,
                contentDescription = cd.titulo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .padding(bottom = 8.dp),
                contentScale = ContentScale.Crop
            )

            Text("Título: ${cd.titulo}", style = MaterialTheme.typography.titleLarge, color = Color.Yellow)
            Text("Artista: ${cd.autor}", style = MaterialTheme.typography.bodyLarge, color = Color.Yellow)
            Text("Año: ${cd.anio}", style = MaterialTheme.typography.bodyMedium, color = Color.Yellow)
            Text("Género: ${cd.genero}", style = MaterialTheme.typography.bodyMedium, color = Color.Yellow)
            Text("Precio: \$${cd.precio}", style = MaterialTheme.typography.bodyMedium, color = Color.Yellow)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Comprar(cd = cd, guardarCompras = guardarCompras)


                if (tipoUsuarioActual == "Admin") {
                    QuitarCDs(cd = cd, viewModel = viewModel, tipoUsuarioActual = tipoUsuarioActual)
                }

            }
        }
    }
}