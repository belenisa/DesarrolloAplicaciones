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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.example.evaluacion2.Data.Modelo.Rol
import com.example.evaluacion2.viewmodel.Usuarios.UsuarioView
import  kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AgregarUsuarioScreen(
    navController: NavController,
    vm: UsuarioView = androidx.lifecycle.viewmodel.compose.viewModel() // o hiltViewModel()
) {
    val cargando by vm.cargando.collectAsState(initial = false)
    val errorMsg by vm.error.collectAsState(initial = null)

    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var rolSeleccionado by remember { mutableStateOf<Rol?>(null) }
    var rolExpanded by remember { mutableStateOf(false) }

    val correoValido = android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()
    val formValido = nombre.isNotBlank() && correoValido && contrasena.isNotBlank() && rolSeleccionado != null

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var pendingNavigate by remember { mutableStateOf(false) }

    LaunchedEffect(errorMsg) {
        errorMsg?.let { scope.launch { snackbarHostState.showSnackbar(it) } }
    }
    LaunchedEffect(pendingNavigate, cargando, errorMsg) {
        if (pendingNavigate && !cargando && errorMsg == null) {
            pendingNavigate = false
            navController.navigate("login") {
                popUpTo("pantallaRegistro") { inclusive = true }
            }
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFFD04))
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
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
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    label = { Text("Correo") },
                    isError = correo.isNotBlank() && !correoValido,
                    supportingText = {
                        if (correo.isNotBlank() && !correoValido) Text("Formato de correo inválido")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = contrasena,
                    onValueChange = { contrasena = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                ExposedRoleDropdown(
                    selected = rolSeleccionado,
                    onSelected = { rolSeleccionado = it },
                    expanded = rolExpanded,
                    onExpandedChange = { rolExpanded = it }
                )

                Button(
                    onClick = {
                        vm.crearUsuario(
                            nombre = nombre.trim(),
                            correo = correo.trim(),
                            Contrasena = contrasena,
                            rol = rolSeleccionado!!
                        )
                        pendingNavigate = true
                        scope.launch { snackbarHostState.showSnackbar("Enviando...") }

                        nombre = ""; correo = ""; contrasena = ""; rolSeleccionado = null
                    },
                    enabled = formValido,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (formValido) Color.Black else Color.Gray
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(horizontal = 8.dp, vertical = 5.dp)
                ) {
                    Text(text = "Aceptar", color = if (formValido) Color.Yellow else Color.LightGray)
                }
            }

            if (cargando) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0x33000000)),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator(color = Color.Black) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
private fun ExposedRoleDropdown(
    selected: Rol?,
    onSelected: (Rol) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit
) {
    val roles = Rol.values().toList()

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selected?.name ?: "",
            onValueChange = {},
            label = { Text("Rol") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            roles.forEach { rol ->
                DropdownMenuItem(
                    text = { Text(rol.name) },
                    onClick = {
                        onSelected(rol)
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}
