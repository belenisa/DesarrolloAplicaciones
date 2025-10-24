package com.example.evaluacion2.Data

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.evaluacion2.Modelo.CD
import com.example.evaluacion2.Modelo.GuardarCompras

@Composable
fun Comprar(cd: CD, guardarCompras: GuardarCompras) {
    Column(modifier = Modifier.padding(16.dp)) {
        Button(
            onClick = {
                guardarCompras.agregar(cd)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Yellow,
                contentColor = Color.Black
            )
        ) {
            Text("Agregar al carrito")
        }
    }
}