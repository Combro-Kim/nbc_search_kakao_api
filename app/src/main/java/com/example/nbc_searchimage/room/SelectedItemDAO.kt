package com.example.nbc_searchimage.room

import androidx.lifecycle.LiveData
import androidx.room.*
// DAO 정의
@Dao
interface SelectedItemDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSelectedItem(selectedItem: SelectedItemEntity)

    @Query("SELECT * FROM selected_items")
    fun getAllSelectedItems(): LiveData<List<SelectedItemEntity>> // LiveData

    @Delete
    suspend fun deleteSelectedItem(selectedItem: SelectedItemEntity)
}