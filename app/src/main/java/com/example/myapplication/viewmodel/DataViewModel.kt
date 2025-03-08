package com.example.myapplication.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.DataEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).dataDao()
    val dataList: LiveData<List<DataEntity>> = dao.getAll()

    private val _rowCount = MutableLiveData(0)
    val rowCount: LiveData<Int> = _rowCount

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchDataAndInsert() {
        viewModelScope.launch {
            _isLoading.value = true  // Show loading
            try {
                val response = RetrofitClient.apiService.getDataAPI()
                if (response.error == 0) {
                    dao.deleteAll()
                    val getEntity = response.data.map { data ->
                        DataEntity(
                            namaProvinsi = data.nama_provinsi,
                            bpsnamaKabupatenKota = data.nama_kabupaten_kota,
                            persentasePeningkatanKapasitasSumberDayaKesehatanManusia = data.tingkat_pengangguran_terbuka,
                            satuan = data.satuan,
                            tahun = data.tahun
                        )
                    }
                    dao.insertAll(getEntity)
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Failed to fetch data: ${e.localizedMessage}")
            } finally {
                _isLoading.value = false
                fetchRowCount()
            }
        }
    }

    fun insertData(
        namaProvinsi: String,
        namaKabupatenKota: String,
        total: String,
        satuan: String,
        tahun: String
    ) {
        viewModelScope.launch {
            val totalValue = total.toFloatOrNull() ?: 0f
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

    fun fetchRowCount() {
        viewModelScope.launch {
            val count = dao.getRowCount()
            _rowCount.postValue(count)
        }
    }


    private val pager = Pager(
        config = PagingConfig(
            pageSize = 20, // Number of items per page
            enablePlaceholders = false
        ),
        pagingSourceFactory = { dao.getPaginatedData() }
    )

    val pagedData = pager.flow.cachedIn(viewModelScope)
}



