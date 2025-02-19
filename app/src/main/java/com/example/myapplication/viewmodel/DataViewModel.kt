package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.DataEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).dataDao()
    val dataList: LiveData<List<DataEntity>> = dao.getAll()

    fun insertData(
        namaProvinsi: String,
        namaKabupatenKota: String,
        total: String,
        satuan: String,
        tahun: String
    ) {
        viewModelScope.launch {
            val totalValue = total.toDoubleOrNull() ?: 0.0
            val tahunValue = tahun.toIntOrNull() ?: 0
            dao.insert(
                DataEntity(

                    namaProvinsi = namaProvinsi,

                    bpsnamaKabupatenKota = namaKabupatenKota,
                    persentasePeningkatanKapasitasSumberDayaKesehatanManusia = totalValue,
                    satuan = satuan,
                    tahun = tahunValue
                )
            )
        }
    }

    fun updateData(data: DataEntity) {
        viewModelScope.launch {
            dao.update(data)
        }
    }

    suspend fun getDataById(id: Int): DataEntity? {
        return withContext(Dispatchers.IO) {
            dao.getById(id)
        }
    }

    fun deleteData(data: DataEntity){
        viewModelScope.launch {
            dao.delete(data)
        }
    }
}
