package com.example.nbc_searchimage.room.repository

import androidx.lifecycle.LiveData
import com.example.nbc_searchimage.room.SelectedItemEntity

//Repository 인터페이스
interface SelectedItemRepository {
    suspend fun insertSelectedItem(selectedItem: SelectedItemEntity)
    fun likedSelectedItems(): LiveData<List<SelectedItemEntity>>
    suspend fun deleteSelectedItem(selectedItem: SelectedItemEntity)

    //todo 이미 아이템이 있는 경우 (삭제, 좋아요 취소)
    // 시도 1. boolean 값으로 받기
    suspend fun isItemSelected(thumbnailUrl: String): Boolean
}