package com.example.evaluacion2.Pantallas

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Contactos() {
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