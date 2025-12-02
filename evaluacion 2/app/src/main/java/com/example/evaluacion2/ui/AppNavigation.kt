
package com.example.evaluacion2.ui

import AgregarUsuario
import Login
import VerCarrito
import VerFormularioCD
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.evaluacion2.Global.AgregarDisco
import com.example.evaluacion2.Global.Encabezado
import com.example.evaluacion2.Global.PieDePagina
import com.example.evaluacion2.Modelo.GuardarCompras
import com.example.evaluacion2.repositorio.UsuarioRepositorio
import com.example.evaluacion2.ui.Pantalla.*
import com.example.evaluacion2.viewmodel.AuthViewModel
import com.example.evaluacion2.viewmodel.ProductoView


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    productoView: ProductoView,
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val guardarCompras = remember { GuardarCompras() }

    var tipoUsuarioActual by remember { mutableStateOf<String?>(null) }
    var nombreUsuarioActual by rememberSaveable { mutableStateOf<String?>(null) }

    val productos by productoView.productos.collectAsState()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    // Cargar productos al iniciar
    LaunchedEffect(Unit) { productoView.cargarProductos() }

    Scaffold(
        modifier = modifier.background(Color.Yellow),
        containerColor = Color.Yellow,
        topBar = {
            Encabezado(
                navController = navController,
                scrollBehavior = scrollBehavior,
                viewModel = productoView
            )
        },
        bottomBar = { PieDePagina(navController) },
        floatingActionButton = {
            if (currentRoute == "producto" && tipoUsuarioActual == "ADMIN") {
                AgregarDisco(onClick = { navController.navigate("formulario") })
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "main", // ✅ Main siempre será la principal
            modifier = Modifier
                .padding(innerPadding)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            // --- Login ---

            composable("login") {
                Login(
                    repo = UsuarioRepositorio(),
                    navController = navController,
                    tipoUsuarioActual = tipoUsuarioActual,
                    nombreUsuarioActual = nombreUsuarioActual,
                    setNombreUsuarioActual = { nombreUsuarioActual = it },
                    setTipoUsuarioActual = { tipoUsuarioActual = it },
                    onLogout = {
                        tipoUsuarioActual = null
                        nombreUsuarioActual = null
                    }
                )
            }


            // --- Pantalla principal ---
            composable("main") {
                MainScreen(navController, scrollBehavior, productoView)
            }

            // --- Lista de productos ---
            composable("producto") {
                PantallaProductos(
                    viewModel = productoView,
                    onProductoClick = { productoSeleccionado ->
                        navController.navigate("detalle/${productoSeleccionado.id}")
                    },
                    guardarCompras = guardarCompras,
                    navController = navController,
                    tipoUsuarioActual = tipoUsuarioActual
                )
            }

            // --- Detalle del producto ---
            composable("detalle/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                val producto = productos.find { it.id == id }
                producto?.let {
                    IndividualCD(
                        producto = it,
                        guardarCompras = guardarCompras,
                        viewModel = productoView,
                        tipoUsuarioActual = tipoUsuarioActual
                    )
                }
            }

            // --- Carrito ---
            composable("carrito") {
                VerCarrito(guardarCompras)
            }

            // --- Formulario para crear/editar producto ---
            composable("formulario") {
                if (tipoUsuarioActual == "ADMIN") {
                    VerFormularioCD(
                        viewModel = productoView,
                        productoActual = null,
                        tipoUsuarioActual = tipoUsuarioActual,
                        onVolver = { navController.popBackStack() }
                    )
                } else {
                    // ✅ Bloqueo y redirección si no es admin
                    LaunchedEffect(Unit) {
                        navController.navigate("login") {
                            popUpTo("main") { inclusive = false }
                        }
                    }
                }
            }

            // --- Contactos ---
            composable("contacto") {
                Contactos()
            }

            // --- Registro de usuario ---
            composable("pantallaRegistro") {
                AgregarUsuario(navController = navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,
    viewModel: ProductoView
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
            .padding(16.dp)
    ) {
        Main(viewModel = viewModel, navController = navController)
    }
}

