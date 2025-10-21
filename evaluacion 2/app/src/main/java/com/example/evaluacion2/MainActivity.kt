package com.example.evaluacion2


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.evaluacion2.Pantallas.Producto
import com.example.evaluacion2.Pantallas.Encabezado
import com.example.evaluacion2.ui.theme.Evaluacion2Theme

class MainActivity : ComponentActivity() {
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



@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(navController)
        }
        composable("producto") {
            Producto()
        }
    }
}

@Composable
fun MainScreen(navController: NavController) {
    Encabezado {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Button(onClick = { navController.navigate("producto") }) {
                Text("Ir a Productos")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Volver(navController)
        }
    }
}

@Composable
fun Volver(navController: NavController) {
    Button(onClick = { navController.navigate("main") }) {
        Text("🏠")
    }
}