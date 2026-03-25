package com.example.matbase.ui

import android.app.Application
import androidx.lifecycle.*
import com.example.matbase.data.AppDatabase
import com.example.matbase.data.Material
import com.example.matbase.data.MaterialDao
import kotlinx.coroutines.launch

class MaterialsViewModel(application: Application) : AndroidViewModel(application) {
    private val materialDao: MaterialDao = AppDatabase.getDatabase(application).materialDao()
    val allMaterials: LiveData<List<Material>> = materialDao.getAllMaterials().asLiveData()

    fun insert(material: Material) = viewModelScope.launch {
        materialDao.insertMaterial(material)
    }

    fun delete(material: Material) = viewModelScope.launch {
        materialDao.deleteMaterial(material)
    }
}