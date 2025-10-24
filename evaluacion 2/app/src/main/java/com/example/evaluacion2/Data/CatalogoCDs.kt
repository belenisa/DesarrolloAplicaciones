package com.example.evaluacion2.Data

import com.example.evaluacion2.Modelo.CD
import com.example.evaluacion2.R

class CatalogoCDs {

    private val listaCDs = mutableListOf<CD>()

    init {
        listaCDs.addAll(
            listOf(
                CD("The Cure", 1982, "Pornography", "Rock", 12990, R.drawable.cure),
                CD("Daft Punk", 2001, "Discovery", "Electrónica", 14990, R.drawable.daftpunk),
                CD("The Doors", 1967, "Strange Days", "Rock", 10990, R.drawable.doors),
                CD("Kids See Ghosts", 2018, "Kids See Ghosts", "Hip-hop", 14990, R.drawable.ksg),
                CD("Low Roar", 2017, "Once in a Long, Long While", "Rock", 10990, R.drawable.lowroar),
                CD("Depeche Mode", 1990, "Violator", "Rock", 13990, R.drawable.violator)
            )
        )
    }

    fun mostrarCDs(): List<CD> = listaCDs
}

