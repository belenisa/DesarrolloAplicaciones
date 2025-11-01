package com.example.evaluacion2.ui.Pantalla

import android.R
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.evaluacion2.Modelo.CD
import kotlinx.coroutines.delay



@Composable
fun Main(viewModel: VerFormularioCD, navController: NavController) {
    val cds by viewModel.primerosTresCDs.collectAsState()
    CarruselCDs(CD = cds, navController = navController)
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
            else -> painterResource(id = R.drawable.ic_menu_gallery)
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