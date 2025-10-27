package com.example.evaluacion2.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.evaluacion2.Data.CatalogoCDs
import com.example.evaluacion2.Global.Encabezado
import com.example.evaluacion2.Global.PieDePagina
import com.example.evaluacion2.Modelo.DataCD
import com.example.evaluacion2.Modelo.GuardarCompras
import com.example.evaluacion2.Pantallas.IndividualCD
import com.example.evaluacion2.Pantallas.Producto
import com.example.evaluacion2.Pantallas.VerCarrito
import com.example.evaluacion2.ui.Pantallaformulario.PantallaProductos
import com.example.evaluacion2.ui.Pantallaformulario.VerFormularioCD
import com.example.evaluacion2.ui.Pantallaformulario.VerFormularioCDFactory

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun AppNavigation(viewModel: VerFormularioCD, modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val catalogo = CatalogoCDs()
    val cds = catalogo.mostrarCDs()
    val guardarCompras = remember { GuardarCompras() }

    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    Scaffold(
        modifier = modifier.background(Color.Yellow),
        containerColor = Color.Yellow,
        topBar = { Encabezado(scrollBehavior) },
        bottomBar = { PieDePagina(navController) },
        floatingActionButton = {
            if (currentRoute == "producto") {
                FloatingActionButton(
                    onClick = { navController.navigate("formulario") },
                    containerColor = Color.Black,
                    contentColor = Color.Yellow
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Ir a formulario")
                }
            }
        }
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
                    viewModel = viewModel,
                    onCDClick = { cdSeleccionado ->
                        navController.navigate("detalle/${cdSeleccionado.titulo}")
                    },
                    guardarCompras = guardarCompras,
                    navController = navController
                )
            }

            composable("detalle/{titulo}") { backStackEntry ->
                val titulo = backStackEntry.arguments?.getString("titulo")
                val cd = cds.find { it.titulo == titulo }
                cd?.let {
                    IndividualCD(cd = it, guardarCompras = guardarCompras)
                }
            }

            composable("carrito") {
                VerCarrito(guardarCompras)
            }

            composable("formulario") { backStackEntry ->
                val context = LocalContext.current
                val dao = remember { DataCD.getInstance(context).CDDao() }
                val factory = remember { VerFormularioCDFactory(dao) }
                val vm: VerFormularioCD = viewModel(backStackEntry, factory = factory)

                PantallaProductos(vm = vm)
            }
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