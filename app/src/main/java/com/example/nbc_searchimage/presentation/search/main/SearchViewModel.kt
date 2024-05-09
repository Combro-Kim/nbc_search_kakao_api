package com.example.nbc_searchimage.presentation.search.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.nbc_searchimage.data.Document
import com.example.nbc_searchimage.retrofit.NetWorkClient
import com.example.nbc_searchimage.room.SelectedItemEntity
import com.example.nbc_searchimage.room.SelectedItemRepository
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: SelectedItemRepository) : ViewModel() {
    private val networkClient = NetWorkClient

    private val _imageItems : MutableLiveData<List<Document>> = MutableLiveData()
    val imageItems: LiveData<List<Document>> get() = _imageItems

    // 좋아요 처리(room)
    private val _isLikedItems: LiveData<List<SelectedItemEntity>> = repository.getAllSelectedItems()
    val isLikedItems: LiveData<List<SelectedItemEntity>> get() = _isLikedItems


    //검색된 데이터
    fun searchImages(query: String) {
        viewModelScope.launch {
        //viewModelScope ?
        // ViewModel이 destory될 때 자식 코루틴들을 자동으로 취소하는 기능을 제공
            try {
                val imageResponse = networkClient.searchNetWork.getImageResponse(query)
                val newDataset = imageResponse.documents ?: emptyList()
                _imageItems.value = newDataset

            } catch (_: Exception) { }
        }
    }

    //마이데이터 저장
    fun saveSelectedItem(selectedItem: Document) {
        viewModelScope.launch {
            // Document에서 SelectedItemEntity로 변환하여 저장
            val entity = SelectedItemEntity(
                displaySiteName = selectedItem.display_sitename,
                datetime = selectedItem.datetime,
                thumbnailUrl = selectedItem.thumbnail_url,
                isLiked = true
            )
            repository.insertSelectedItem(entity)
        }
    }
}

//todo SearchViewModelFactory 구현해보자!! -> 7강의
class SearchViewModelFactory(private val repository: SelectedItemRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
//SelectedItemRepository 인스턴스를 받는다. Repository를 생성