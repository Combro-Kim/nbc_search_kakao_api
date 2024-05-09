package com.example.nbc_searchimage.room

import androidx.lifecycle.LiveData

class SelectedItemRepository(private val selectedItemDao: SelectedItemDAO) {

    suspend fun insertSelectedItem(selectedItem: SelectedItemEntity) {
        selectedItemDao.insertSelectedItem(selectedItem)
    }

    fun getAllSelectedItems(): LiveData<List<SelectedItemEntity>> {
        return selectedItemDao.getAllSelectedItems()
    }

    suspend fun deleteSelectedItem(selectedItem: SelectedItemEntity) {
        selectedItemDao.deleteSelectedItem(selectedItem)
    }
}