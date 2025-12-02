
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.evaluacion2.Modelo.Usuarios
import com.example.evaluacion2.repositorio.UsuarioRepositorio


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Login(
    listaUsuarios: ListaUsuarios, // ✅ lista global
    onLogingClick: (Usuarios) -> Unit,
    onLogout: () -> Unit,
    navController: NavController,
    tipoUsuarioActual: String?,
    nombreUsuarioActual: String?,
    setNombreUsuarioActual: (String?) -> Unit,
    setTipoUsuarioActual: (String?) -> Unit
) {
    var nombre by rememberSaveable { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            if (tipoUsuarioActual != null) {
                SalirUsuario(navController = navController, onLogout = {
                    onLogout()
                    setNombreUsuarioActual(null)
                    setTipoUsuarioActual(null)
                })
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Titulo()
        Spacer(modifier = Modifier.height(24.dp))

        NombreUsuario(nombre, { nombre = it }, nombreUsuarioActual)
        Spacer(modifier = Modifier.height(16.dp))

        if (tipoUsuarioActual == null) {
            Contrasena(contrasena) { contrasena = it }
            Spacer(modifier = Modifier.height(24.dp))
        }

        if (tipoUsuarioActual == null) {
            IngresarUsuario(
                nombre = nombre,
                contrasena = contrasena,
                usuarios = listaUsuarios.obtenerUsuarios(), // ✅ usa lista global
                onLogingClick = {
                    onLogingClick(it)
                    setNombreUsuarioActual(it.nombre) // ✅ propiedad correcta
                    setTipoUsuarioActual(it.rol?.rol?.name ?: "CLIENTE") // ✅ rol seguro
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onError = { error = true }
            )
        }

        if (error) {
            Text(
                text = "Usuario o contraseña incorrectos",
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        if (tipoUsuarioActual == null || tipoUsuarioActual != "Cliente") {
            Registro(navController)
        }
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
fun NombreUsuario(
    nombre: String,
    onNombreChange: (String) -> Unit,
    nombreUsuarioActual: String?
) {
    if (nombreUsuarioActual != null) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = nombreUsuarioActual,
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    } else {
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
        visualTransformation = PasswordVisualTransformation(),
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
            val usuario = usuarios.find {
                it.nombre.equals(nombre.trim(), ignoreCase = true) &&
                        (it.contrasena ?: "") == contrasena
            }
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

@Composable
fun SalirUsuario(navController: NavController, onLogout: () -> Unit) {
    Text(
        text = "Salir",
        color = Color(0xFF000000),
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        modifier = Modifier.clickable {
            onLogout()
            navController.navigate("login") {
                popUpTo("main") { inclusive = true }
            }
        }
    )
}

