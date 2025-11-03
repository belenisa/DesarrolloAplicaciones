package com.example.evaluacion2.ui.Pantalla

//import android.R
import com.example.evaluacion2.R
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.evaluacion2.Modelo.CD
import kotlinx.coroutines.delay




@Composable
fun Main(viewModel: VerFormularioCD, navController: NavController) {
    val cds by viewModel.primerosTresCDs.collectAsState()
    Logo()
    Spacer(modifier = Modifier.height(16.dp))
    CarruselCDs(CD = cds, navController = navController)
    Spacer(modifier = Modifier.height(16.dp))
    Iconos(navController)
}




@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarruselCDs(CD: List<CD>, navController: NavController) {
    val cdsLimitados = CD.take(3)
    val pagerState = rememberPagerState(
        initialPage = 1000,
        pageCount = { Int.MAX_VALUE }
    )

    if (cdsLimitados.isEmpty()) {
        Text("No hay CDs disponibles")
        return
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % Int.MAX_VALUE
            pagerState.animateScrollToPage(nextPage)
        }
    }

    HorizontalPager(state = pagerState) { page ->
        val index = page % cdsLimitados.size
        val cd = cdsLimitados[index]
        val painter = when {
            cd.imagenUri?.isNotBlank() == true -> rememberAsyncImagePainter(model = cd.imagenUri)
            cd.imagenResId != null -> painterResource(id = cd.imagenResId)
            else -> painterResource(id = android.R.drawable.ic_menu_gallery)
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp)
                .clickable {
                    navController.navigate("detalle/${cd.titulo}")
                },
            colors = CardDefaults.cardColors(containerColor = Color.Black)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painter,
                    contentDescription = cd.titulo,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(180.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = cd.titulo,
                    color = Color.Yellow,
                    fontSize = 20.sp
                )
                Text(
                    text = cd.autor,
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun Iconos(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val cardSize = 96.dp
        val imageSize = 64.dp

        // Carta 1

        Card(
            modifier = Modifier
                .size(cardSize)
                .clickable {
                    navController.navigate("producto")
                },
            colors = CardDefaults.cardColors(containerColor = Color.Black),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.abc),
                    contentDescription = "Ícono disco",
                    modifier = Modifier
                        .size(imageSize)
                        .clip(RoundedCornerShape(12.dp)), // Bordes redondeados en la imagen
                    contentScale = ContentScale.Fit
                )
            }
        }


        // Carta 2
        Card(
            modifier = Modifier
                .size(cardSize)
                .clickable {
                    navController.navigate("carrito")
                },
            colors = CardDefaults.cardColors(containerColor = Color.Black),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.carrito),
                    contentDescription = "Ícono carrito",
                    modifier = Modifier
                        .size(imageSize)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Fit
                )
            }
        }

        // Carta 3
        Card(
            modifier = Modifier
                .size(cardSize)
                .clickable {
                    navController.navigate("contacto")
                },
            colors = CardDefaults.cardColors(containerColor = Color.Black),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.nosotros),
                    contentDescription = "Ícono nosotros",
                    modifier = Modifier
                        .size(imageSize)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Composable
fun Logo() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.disco),
                    contentDescription = "Logo RageMusic",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "RageMusic",
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = "contacto@ragemusica.com",
                fontSize = 10.sp,
                color = Color.Gray
            )
        }
    }
}