
package com.example.evaluacion2.ui.Pantalla

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.evaluacion2.Data.Modelo.Producto
import com.example.evaluacion2.R
import com.example.evaluacion2.viewmodel.ProductoViewModel
import kotlinx.coroutines.delay

@Composable
fun Main(viewModel: ProductoViewModel, navController: NavController) {
    val productos by viewModel.productos.collectAsState()

    Logo()
    Spacer(modifier = Modifier.height(16.dp))
    CarruselProductos(productos = productos, navController = navController)
    Spacer(modifier = Modifier.height(16.dp))
    Iconos(navController)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarruselProductos(productos: List<Producto>, navController: NavController) {
    val productosLimitados = productos.take(3)
    val pagerState = rememberPagerState(initialPage = 1000, pageCount = { Int.MAX_VALUE })

    if (productosLimitados.isEmpty()) {
        Text("No hay productos disponibles")
        return
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % Int.MAX_VALUE)
        }
    }

    HorizontalPager(state = pagerState) { page ->
        val index = page % productosLimitados.size
        val producto = productosLimitados[index]

        val painter = painterResource(id = android.R.drawable.ic_menu_gallery)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp)
                .clickable {
                    navController.navigate("detalle/${producto.id}")
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
                    contentDescription = producto.nombre,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(180.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = producto.nombre, color = Color.Yellow, fontSize = 20.sp)
                Text(text = "$${producto.precio}", color = Color.White, fontSize = 16.sp)
                producto.artistas?.artista?.nombre?.let {
                    Text(text = "Artista: $it", color = Color.Gray, fontSize = 14.sp)
                }
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

        Card(
            modifier = Modifier
                .size(cardSize)
                .clickable { navController.navigate("producto") },
            colors = CardDefaults.cardColors(containerColor = Color.Black),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.abc),
                    contentDescription = "Ícono producto",
                    modifier = Modifier.size(imageSize),
                    contentScale = ContentScale.Fit
                )
            }
        }

        Card(
            modifier = Modifier
                .size(cardSize)
                .clickable { navController.navigate("carrito") },
            colors = CardDefaults.cardColors(containerColor = Color.Black),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.carrito),
                    contentDescription = "Ícono carrito",
                    modifier = Modifier.size(imageSize),
                    contentScale = ContentScale.Fit
                )
            }
        }

        Card(
            modifier = Modifier
                .size(cardSize)
                .clickable { navController.navigate("contacto") },
            colors = CardDefaults.cardColors(containerColor = Color.Black),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.nosotros),
                    contentDescription = "Ícono nosotros",
                    modifier = Modifier.size(imageSize),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Composable
fun Logo() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.disco),
                contentDescription = "Logo RageMusic",
                modifier = Modifier.size(48.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "RageMusic", fontSize = 20.sp, color = Color.Black)
        }
        Text(text = "contacto@ragemusica.com", fontSize = 10.sp, color = Color.Gray)
    }
}
