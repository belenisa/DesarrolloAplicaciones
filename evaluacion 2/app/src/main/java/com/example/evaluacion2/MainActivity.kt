package com.example.evaluacion2


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.evaluacion2.Data.CatalogoCDs
import com.example.evaluacion2.Global.Encabezado
import com.example.evaluacion2.Global.PieDePagina
import com.example.evaluacion2.Modelo.CD
import com.example.evaluacion2.Modelo.GuardarCompras
import com.example.evaluacion2.Pantallas.IndividualCD
import com.example.evaluacion2.Pantallas.Producto
import com.example.evaluacion2.Pantallas.VerCarrito
import com.example.evaluacion2.ui.theme.Evaluacion2Theme


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Evaluacion2Theme {
                AppNavigation()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val catalogo = CatalogoCDs()
    val cds = catalogo.mostrarCDs()
    val guardarCompras = remember { GuardarCompras() }

    Scaffold(
        modifier = modifier.background(Color.Yellow),
        containerColor = Color.Yellow,
        topBar = { Encabezado(scrollBehavior) },
        bottomBar = { PieDePagina(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "main",
            modifier = Modifier
                .padding(innerPadding)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            composable("main") {
                MainScreen(navController, scrollBehavior)
            }

            composable("producto") {
                Producto(
                    onCDClick = { cdSeleccionado ->
                        navController.navigate("detalle/${cdSeleccionado.titulo}")
                    },
                    guardarCompras = guardarCompras
                )
            }

            composable("detalle/{titulo}") { backStackEntry ->
                val titulo = backStackEntry.arguments?.getString("titulo")
                val cd = cds.find { it.titulo == titulo }
                cd?.let {
                    IndividualCD(cd = it, guardarCompras = guardarCompras)
                }
            }
            composable("carrito") { VerCarrito(guardarCompras) }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, scrollBehavior: TopAppBarScrollBehavior) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Yellow)
        .padding(16.dp)) {
    }
}
