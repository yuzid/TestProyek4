package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data_table")
data class DataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val namaProvinsi: String,
    val bpsnamaKabupatenKota: String,
    val persentasePeningkatanKapasitasSumberDayaKesehatanManusia: Float,
    val satuan: String,
    val tahun: Int
)
