package com.example.nbc_searchimage.room.repository

import androidx.lifecycle.LiveData
import com.example.nbc_searchimage.room.SelectedItemEntity

//Repository 인터페이스
interface SelectedItemRepository {
    suspend fun insertSelectedItem(selectedItem: SelectedItemEntity)
    fun likedSelectedItems(): LiveData<List<SelectedItemEntity>>
    suspend fun deleteSelectedItem(selectedItem: SelectedItemEntity)
}