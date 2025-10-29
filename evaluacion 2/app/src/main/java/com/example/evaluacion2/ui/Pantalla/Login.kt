package com.example.evaluacion2.ui.Pantalla

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.evaluacion2.Data.ListaUsuarios
import com.example.evaluacion2.Modelo.Usuarios



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Login(onLogingClick: (Usuarios) -> Unit, navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    val listaUsuarios = ListaUsuarios().obtenerUsuarios()
    var error by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Titulo()
        Spacer(modifier = Modifier.height(24.dp))

        NombreUsuario(nombre) { nombre = it }
        Spacer(modifier = Modifier.height(16.dp))

        Contrasena(contrasena) { contrasena = it }
        Spacer(modifier = Modifier.height(24.dp))

        IngresarUsuario(
            nombre = nombre,
            contrasena = contrasena,
            usuarios = listaUsuarios,
            onLogingClick = {
                onLogingClick(it)
                navController.navigate("main") {
                    popUpTo("login") { inclusive = true }
                }
            },
            onError = { error = true }
        )


        if (error) {
            Text(
                text = "Usuario o contraseña incorrectos",
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Registro(navController)
    }
}


@Composable
fun Titulo() {
    Text(
        text = "Login",
        color = Color(0xFF000000),
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    )
}


@Composable
fun NombreUsuario(nombre: String, onNombreChange: (String) -> Unit) {
    OutlinedTextField(
        value = nombre,
        onValueChange = onNombreChange,
        label = { Text("Usuario") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(horizontal = 8.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedIndicatorColor = Color.Black,
            unfocusedIndicatorColor = Color.Gray,
            focusedLabelColor = Color.Black,
            cursorColor = Color.Black,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        )
    )
}

@Composable
fun Contrasena(contrasena: String, onContrasenaChange: (String) -> Unit) {
    OutlinedTextField(
        value = contrasena,
        onValueChange = onContrasenaChange,
        label = { Text("Contraseña") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(horizontal = 8.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedIndicatorColor = Color.Black,
            unfocusedIndicatorColor = Color.Gray,
            focusedLabelColor = Color.Black,
            cursorColor = Color.Black,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        )
    )
}


@Composable
fun Registro(navController: NavController) {
    Text(
        text = "¿No tienes usuario?",
        color = Color(0xFF000000),
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier.clickable {
            navController.navigate("pantallaRegistro")
        }
    )
}



@Composable
fun IngresarUsuario(
    nombre: String,
    contrasena: String,
    usuarios: List<Usuarios>,
    onLogingClick: (Usuarios) -> Unit,
    onError: () -> Unit
) {
    Button(
        onClick = {
            val usuario = usuarios.find { it.Usuario == nombre && it.Contraseña == contrasena }
            if (usuario != null) {
                onLogingClick(usuario)
            } else {
                onError()
            }
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(horizontal = 8.dp, vertical = 5.dp)
    ) {
        Text(
            text = "Ingresar",
            color = Color(0xFFFFFD04)
        )
    }
}



