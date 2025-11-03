package com.example.evaluacion2.ui.Pantalla

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.evaluacion2.Data.ListaUsuarios
import com.example.evaluacion2.Modelo.Usuarios
import kotlinx.coroutines.launch
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AgregarUsuario(
    listaUsuarios: ListaUsuarios,
    tipoUsuarioActual: String?,
    navController: NavController
) {
    var usuario by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var tipoUsuario by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val usuarioYaExiste = listaUsuarios.usuarioExiste(usuario)

    val correoYaExiste = listaUsuarios.correoExiste(correo)
    val correoValido = android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()


    val fechaValida = try {
        LocalDate.parse(fechaNacimiento)
        true
    } catch (e: Exception) {
        false
    }


    val allFieldsFilled = usuario.isNotBlank() &&
            nombre.isNotBlank() &&
            apellido.isNotBlank() &&
            correo.isNotBlank() &&
            contraseña.isNotBlank() &&
            fechaNacimiento.isNotBlank() &&
            fechaValida &&
            correoValido &&
            !correoYaExiste &&
            !usuarioYaExiste &&
            (tipoUsuarioActual != "Admin" || tipoUsuario.isNotBlank())



    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFFD04))
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Registrar Usuario",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                OutlinedTextField(
                    value = usuario,
                    onValueChange = { usuario = it },
                    label = { Text("Usuario") },
                    isError = usuarioYaExiste && usuario.isNotBlank(),
                    supportingText = {
                        if (usuarioYaExiste && usuario.isNotBlank()) {
                            Text("Este nombre de usuario ya está en uso")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = apellido, onValueChange = { apellido = it }, label = { Text("Apellido") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    label = { Text("Correo") },
                    isError = (!correoValido || correoYaExiste) && correo.isNotBlank(),
                    supportingText = {
                        when {
                            correoYaExiste && correo.isNotBlank() -> Text("Este correo ya está registrado")
                            !correoValido && correo.isNotBlank() -> Text("Formato de correo inválido")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = fechaNacimiento,
                    onValueChange = { fechaNacimiento = it },
                    label = { Text("Fecha de Nacimiento (YYYY-MM-DD)") },
                    isError = !fechaValida && fechaNacimiento.isNotBlank(),
                    supportingText = {
                        if (!fechaValida && fechaNacimiento.isNotBlank()) {
                            Text("Formato de fecha inválido")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(value = contraseña, onValueChange = { contraseña = it }, label = { Text("Contraseña") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())

                if (tipoUsuarioActual == "Admin") {
                    OutlinedTextField(value = tipoUsuario, onValueChange = { tipoUsuario = it }, label = { Text("Tipo de Usuario") }, modifier = Modifier.fillMaxWidth())
                }

                Aceptar(
                    onClick = {
                        // Validación final por seguridad
                        if (usuarioYaExiste) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("El nombre de usuario ya existe")
                            }
                            return@Aceptar
                        }

                        val fecha = try {
                            LocalDate.parse(fechaNacimiento)
                        } catch (e: Exception) {
                            null
                        }

                        val nuevoUsuario = Usuarios(
                            Usuario = usuario,
                            Nombre = nombre,
                            Apellido = apellido,
                            correo = correo,
                            FechaNacimiento = fecha,
                            Contraseña = contraseña,
                            TipoUsuario = if (tipoUsuarioActual == "Admin") tipoUsuario else "Cliente"
                        )

                        listaUsuarios.agregarUsuario(nuevoUsuario)

                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Usuario registrado con éxito")
                        }

                        // Limpiar campos
                        usuario = ""
                        nombre = ""
                        apellido = ""
                        correo = ""
                        fechaNacimiento = ""
                        contraseña = ""
                        tipoUsuario = ""

                        // Navegar a login
                        navController.navigate("login") {
                            popUpTo("pantallaRegistro") { inclusive = true }
                        }
                    },
                    navController = navController,
                    enabled = allFieldsFilled
                )
            }
        }
    }
}

@Composable
fun Aceptar(onClick: () -> Unit, navController: NavController, enabled: Boolean) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) Color(0xFF000000) else Color.Gray
        ),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(horizontal = 8.dp, vertical = 5.dp)
    ) {
        Text(
            text = "Aceptar",
            color = if (enabled) Color.Yellow else Color.LightGray
        )
    }
}
