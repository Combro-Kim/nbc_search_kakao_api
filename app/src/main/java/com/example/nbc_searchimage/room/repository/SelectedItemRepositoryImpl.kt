package com.example.nbc_searchimage.room.repository

import androidx.lifecycle.LiveData
import com.example.nbc_searchimage.room.SelectedItemDAO
import com.example.nbc_searchimage.room.SelectedItemEntity


class SelectedItemRepositoryImpl(private val selectedItemDao: SelectedItemDAO): SelectedItemRepository {
    //선택한 아이템 추가
    override suspend fun insertSelectedItem(selectedItem: SelectedItemEntity) {
        selectedItemDao.getAllSelectedItems()
    }

    //좋아요
    override fun likedSelectedItems(): LiveData<List<SelectedItemEntity>> {
        return selectedItemDao.getAllSelectedItems()
    }

    //선택한 아이템 삭제
    override suspend fun deleteSelectedItem(selectedItem: SelectedItemEntity) {
        selectedItemDao.deleteSelectedItem(selectedItem)
    }
}