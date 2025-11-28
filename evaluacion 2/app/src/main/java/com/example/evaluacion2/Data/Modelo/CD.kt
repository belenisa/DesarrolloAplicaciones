package com.example.evaluacion2.Data.Modelo

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class CD(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "autor") val autor: String,
    @ColumnInfo(name = "anio") val anio: Int,
    @ColumnInfo(name = "titulo") val titulo: String,
    @ColumnInfo(name = "genero") val genero: String,
    @ColumnInfo(name = "precio") val precio: Int,
    @ColumnInfo(name = "imagenResId")  val imagenResId: Int?,
    @ColumnInfo(name = "imagenUri") val imagenUri: String? // para galería
)

