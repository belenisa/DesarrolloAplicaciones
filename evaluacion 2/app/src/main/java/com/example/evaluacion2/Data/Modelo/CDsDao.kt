package com.example.evaluacion2.Modelo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CDsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(cd: CD)

    @Query("SELECT * FROM productos")
    fun getAll(): Flow<List<CD>>

    @Query("DELETE FROM productos")
    suspend fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarVarios(cds: List<CD>)

    // Buscar por título o autor

    @Query("SELECT * FROM productos WHERE titulo LIKE '%' || :query || '%' OR autor LIKE '%' || :query || '%'")
    fun buscarProductos(query: String): Flow<List<CD>>

    @Query("SELECT * FROM productos LIMIT 3")
    fun getPrimerosTresCDs(): Flow<List<CD>>

    @Delete
    suspend fun eliminarCD(cd: CD)

}
