package com.example.nbc_searchimage.room

import androidx.lifecycle.LiveData

class SelectedItemRepository(private val selectedItemDao: SelectedItemDAO) {

    //선택된 아이템 추가
    suspend fun insertSelectedItem(selectedItem: SelectedItemEntity) {
        selectedItemDao.insertSelectedItem(selectedItem)
    }

    //좋아요
    fun getAllSelectedItems(): LiveData<List<SelectedItemEntity>> {
        return selectedItemDao.getAllSelectedItems()
    }

    //선택한 아이템 삭제
    suspend fun deleteSelectedItem(selectedItem: SelectedItemEntity) {
        selectedItemDao.deleteSelectedItem(selectedItem)
    }
}