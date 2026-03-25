package com.example.matbase.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MaterialDao {
    @Query("SELECT * FROM materials ORDER BY dateAdded DESC")
    fun getAllMaterials(): Flow<List<Material>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMaterial(material: Material)

    @Delete
    suspend fun deleteMaterial(material: Material)
}