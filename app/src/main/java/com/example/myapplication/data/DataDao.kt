package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface DataDao {

    @Insert
    suspend fun insert(data: DataEntity)

    @Update
    suspend fun update(data: DataEntity)

    @Query("SELECT * FROM data_table ORDER BY id DESC")
    fun getAll(): LiveData<List<DataEntity>>

    @Query("SELECT * FROM data_table WHERE id = :dataId")
    suspend fun getById(dataId: Int): DataEntity?

    @Delete
    suspend fun delete(data: DataEntity)

    @Query("SELECT COUNT(*) FROM data_table")
    suspend fun getRowCount(): Int

    @Query("DELETE FROM data_table")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dataList: List<DataEntity>)

    @Query("SELECT * FROM data_table ORDER BY id ASC")
    fun getPaginatedData(): PagingSource<Int, DataEntity>
}
