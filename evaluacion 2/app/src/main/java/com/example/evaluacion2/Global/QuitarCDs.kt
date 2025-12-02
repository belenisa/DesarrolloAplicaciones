
package com.example.evaluacion2.Global

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.evaluacion2.Data.Modelo.Producto
import com.example.evaluacion2.viewmodel.ProductoView

@Composable
fun QuitarProducto(producto: Producto, viewModel: ProductoView, tipoUsuarioActual: String) {
    if (tipoUsuarioActual == "Admin") {
        IconButton(
            onClick = { viewModel.eliminarProducto(producto.id ?: 0) },
            modifier = Modifier.size(50.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Eliminar producto",
                tint = Color.White
            )
        }
    }
}
