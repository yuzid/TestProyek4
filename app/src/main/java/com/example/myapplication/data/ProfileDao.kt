package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
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
    suspend fun getProfile(): DataProfile?]

    // New function to update only the profile image URI
    @Query("UPDATE data_profile SET profileImageUri = :imageUri WHERE id = 1")
    suspend fun updateProfileImage(imageUri: String?)
}
