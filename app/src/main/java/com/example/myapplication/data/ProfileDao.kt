package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProfileDao {

    @Insert
    suspend fun insert(data: DataProfile)

    @Update
    suspend fun update(data: DataProfile)

    @Query("SELECT * FROM data_profile ORDER BY id DESC")
    fun getAll(): LiveData<List<DataProfile>>

    @Query("SELECT * FROM data_profile WHERE id = :dataId")
    suspend fun getById(dataId: Int): DataProfile?

    @Delete
    suspend fun delete(data: DataProfile)

    @Query("SELECT * FROM data_profile WHERE id = 1 LIMIT 1")
    suspend fun getProfile(): DataProfile?
}