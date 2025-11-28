package com.example.evaluacion2.ui.Pantalla

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.evaluacion2.R


@Composable
fun Contactos() {
    Scaffold(
        containerColor = Color(0xFFFFEB3B), // Fondo amarillo

        content = { _ ->
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Logo()
                Spacer(modifier = Modifier.height(16.dp))
                Encabezado()
                Spacer(modifier = Modifier.height(16.dp))
                Relleno()
                Spacer(modifier = Modifier.height(16.dp))
                Equipo()
            }
        }
    )
}

@Composable
fun Encabezado() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "¿Quienes Somos?",
            color = Color(0xFF000000),
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp
        )
    }
}

@Composable
fun Relleno() {
    val scrollState = rememberScrollState()

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 300.dp, max = 500.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(scrollState) // Scroll interno para texto largo
        ) {
            Text(
                text = "En RageMusic, creemos que la música no solo se escucha, sino que se vive. Nacimos en Chile como una tienda virtual apasionada por el sonido auténtico y el poder de los formatos físicos, ofreciendo una amplia selección de CDs y vinilos que recorren géneros, épocas y estilos.\n\n" +
                        "Nuestra misión es conectar a los melómanos con la experiencia única de coleccionar música, desde ediciones limitadas hasta clásicos inmortales. Nos enorgullece ser un espacio donde tanto coleccionistas experimentados como nuevos fanáticos puedan descubrir piezas que marcan la diferencia en cada colección.",
                color = Color(0xFFFFEB3B),
                fontSize = 20.sp
            )
        }
    }
}




@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Equipo() {
    val imagenes = listOf(R.drawable.filidey, R.drawable.gabriel, R.drawable.belen)
    val nombres = listOf("Filidey", "Gabriel", "Belén")
    val pagerState = rememberPagerState(
        initialPage = 1000,
        pageCount = { Int.MAX_VALUE }
    )

    HorizontalPager(state = pagerState) { page ->
        val index = page % imagenes.size
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Black)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = imagenes[index]),
                    contentDescription = nombres[index],
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(180.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = nombres[index],
                    color = Color.Yellow,
                    fontSize = 20.sp
                )
            }
        }
    }
}

