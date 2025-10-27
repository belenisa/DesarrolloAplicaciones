package com.example.evaluacion2.Data

import android.content.Context
import android.net.Uri
import androidx.annotation.DrawableRes
import com.example.evaluacion2.Modelo.CD
import com.example.evaluacion2.R
import androidx.core.net.toUri

class CatalogoCDs {

    private val listaCDs = mutableListOf<CD>()

    init {
        listaCDs.addAll(
            listOf(
                CD(1, "The Cure", 1982, "Pornography", "Rock", 12990, R.drawable.cure, null),
                CD(2, "Daft Punk", 2001, "Discovery", "Electrónica", 14990, R.drawable.daftpunk, null),
                CD(3, "The Doors", 1967, "Strange Days", "Rock", 10990, R.drawable.doors, null),
                CD(4, "Kids See Ghosts", 2018, "Kids See Ghosts", "Hip-hop", 14990, R.drawable.ksg, null),
                CD(5, "Low Roar", 2017, "Once in a Long, Long While", "Rock", 10990, R.drawable.lowroar, null),
                CD(6, "Depeche Mode", 1990, "Violator", "Rock", 13990, R.drawable.violator, null)
            )
        )
    }

    fun mostrarCDs(): List<CD> = listaCDs
}