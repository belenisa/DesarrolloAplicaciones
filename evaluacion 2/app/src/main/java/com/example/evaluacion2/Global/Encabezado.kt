package com.example.evaluacion2.Global

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.evaluacion2.R
import androidx.navigation.NavController
import com.example.evaluacion2.ui.Pantalla.VerFormularioCD

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Encabezado(
    navController: NavController,
    viewModel: VerFormularioCD,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    content: @Composable () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .background(Color.Black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Buscar(viewModel = viewModel, navController = navController)

            Button(
                onClick = {navController.navigate("login") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.Yellow
                )
            ) {
                Text("Usuario")
            }
        }

        TopAppBar(
            title = { Titulo() },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Black,
                scrolledContainerColor = Color.Black
            ),
            scrollBehavior = scrollBehavior
        )

        content()
    }
}



@Composable
fun Buscar(viewModel: VerFormularioCD, navController: NavController) {
    val resultados by viewModel.resultadosBusqueda.collectAsState()
    var texto by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = texto,
            onValueChange = {
                texto = it
                viewModel.actualizarBusqueda(it)
                expanded = texto.isNotEmpty() && resultados.isNotEmpty()
            },
            label = { Text("Buscar CD") },
            singleLine = true,
            modifier = Modifier
                .width(250.dp)
                .height(60.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Yellow,
                unfocusedTextColor = Color.Yellow,
                focusedIndicatorColor = Color.Yellow,
                unfocusedIndicatorColor = Color.Gray,
                focusedLabelColor = Color.Yellow,
                cursorColor = Color.Yellow,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(250.dp)
                .background(Color.Black)
        ) {
            resultados.forEach { cd ->
                DropdownMenuItem(
                    text = {
                        Column {
                            Text(cd.titulo, color = Color.Yellow, fontWeight = FontWeight.Bold)
                            Text(cd.autor, color = Color.Gray)
                        }
                    },
                    onClick = {
                        expanded = false
                        navController.navigate("detalle/${cd.titulo}")
                    }
                )
            }
        }
    }
}



@Composable
fun Titulo(){
    Text(
        text = stringResource(id = R.string.app_name),
        color = Color(0xFFFFF176),
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    )
}
