package com.example.evaluacion2.ui

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.example.evaluacion2.Global.Encabezado
import com.example.evaluacion2.Global.PieDePagina
import com.example.evaluacion2.Modelo.DataCD
import com.example.evaluacion2.Modelo.GuardarCompras
import com.example.evaluacion2.ui.Pantalla.IndividualCD
import com.example.evaluacion2.ui.Pantalla.Producto
import com.example.evaluacion2.ui.Pantalla.VerCarrito
import com.example.evaluacion2.ui.Pantalla.Login
import com.example.evaluacion2.ui.Pantalla.PantallaProductos
import com.example.evaluacion2.ui.Pantalla.VerFormularioCD
import com.example.evaluacion2.ui.Pantalla.VerFormularioCDFactory
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.evaluacion2.Data.ListaUsuarios
import com.example.evaluacion2.Global.AgregarDisco
import com.example.evaluacion2.ui.Pantalla.AgregarUsuario
import com.example.evaluacion2.ui.Pantalla.Contactos
import com.example.evaluacion2.ui.Pantalla.Main
import com.example.evaluacion2.ui.Pantalla.SalirUsuario


@RequiresApi(Build.VERSION_CODES.O)@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(viewModel: VerFormularioCD, modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val guardarCompras = remember { GuardarCompras() }
    var tipoUsuarioActual by remember { mutableStateOf<String?>(null) }

    val cds by viewModel.productos.collectAsState()

    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    var nombreUsuarioActual by rememberSaveable { mutableStateOf<String?>(null) }
    val listaUsuariosGlobal = remember { ListaUsuarios() }

    Scaffold(
        modifier = modifier.background(Color.Yellow),
        containerColor = Color.Yellow,
        topBar = {
            Encabezado(
                navController = navController,
                scrollBehavior = scrollBehavior,
                viewModel = viewModel
            )
        },
        bottomBar = { PieDePagina(navController) },
        floatingActionButton = {
            if (currentRoute == "producto" && tipoUsuarioActual == "Admin") {
                AgregarDisco(onClick = { navController.navigate("formulario") })
            }
        }
    ) { innerPadding ->
    NavHost(
            navController = navController,
            startDestination = "main", // ← Aquí se cambia la pantalla inicial
            modifier = Modifier
                .padding(innerPadding)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {

        composable("login") {
            Login(
                listaUsuarios = listaUsuariosGlobal,
                onLogingClick = { usuario ->
                    tipoUsuarioActual = usuario.TipoUsuario
                    nombreUsuarioActual = usuario.Usuario
                },
                onLogout = {
                    tipoUsuarioActual = null
                    nombreUsuarioActual = null
                },
                tipoUsuarioActual = tipoUsuarioActual,
                nombreUsuarioActual = nombreUsuarioActual,
                setNombreUsuarioActual = { nombreUsuarioActual = it },
                setTipoUsuarioActual = { tipoUsuarioActual = it },
                navController = navController
            )
        }

            composable("main") {
                MainScreen(navController, scrollBehavior, viewModel)
            }

            composable("producto") {
                Producto(
                    viewModel = viewModel,
                    onCDClick = { cdSeleccionado ->
                        navController.navigate("detalle/${cdSeleccionado.titulo}")
                    },
                    guardarCompras = guardarCompras,
                    navController = navController,
                    tipoUsuarioActual = tipoUsuarioActual
                )
            }

            composable("detalle/{titulo}") { backStackEntry ->
                val titulo = backStackEntry.arguments?.getString("titulo")
                val cd = cds.find { it.titulo == titulo }
                cd?.let {
                    IndividualCD(
                        cd = it,
                        guardarCompras = guardarCompras,
                        viewModel = viewModel,
                        tipoUsuarioActual = tipoUsuarioActual
                    )
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

            composable("contacto") {
                Contactos()
            }


            composable("pantallaRegistro") {
                AgregarUsuario(
                    listaUsuarios = listaUsuariosGlobal,
                    tipoUsuarioActual = tipoUsuarioActual,
                    navController = navController
                )
            }

        }
    }
}







@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,
    viewModel: VerFormularioCD
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
