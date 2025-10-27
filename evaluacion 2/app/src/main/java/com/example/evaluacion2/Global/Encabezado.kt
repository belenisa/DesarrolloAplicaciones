package com.example.evaluacion2.Global

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.evaluacion2.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Encabezado(
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    content: @Composable () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .background(Color.Black)
    ) {
        Buscar()
        Text("usuario", color = Yellow)
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
fun Buscar() {
    var texto by remember { mutableStateOf("") }

    OutlinedTextField(
        value = texto,
        onValueChange = { texto = it },
        label = { Text("Buscar") },
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
