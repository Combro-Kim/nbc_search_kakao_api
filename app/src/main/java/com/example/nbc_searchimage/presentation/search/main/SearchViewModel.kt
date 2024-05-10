package com.example.nbc_searchimage.presentation.search.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.nbc_searchimage.presentation.search.repository.SearchRepositoryImpl
import com.example.nbc_searchimage.network.RetrofitClient
import com.example.nbc_searchimage.presentation.search.model.SearchItemEntity
import com.example.nbc_searchimage.presentation.search.repository.SearchRepository
import com.example.nbc_searchimage.room.MyDatabase
import com.example.nbc_searchimage.room.SelectedItemEntity
import com.example.nbc_searchimage.room.repository.SelectedItemRepository
import com.example.nbc_searchimage.room.repository.SelectedItemRepositoryImpl
import kotlinx.coroutines.launch

class SearchViewModel(private val searchRepository: SearchRepository,
    private val selectedItemRepository :SelectedItemRepository ) : ViewModel() {

    private val _getSearchImageList: MutableLiveData<List<SearchItemEntity>> = MutableLiveData()
    val getSearchImageList: LiveData<List<SearchItemEntity>> get() = _getSearchImageList


    // 좋아요 처리(room)
    private val _isLikedItems: LiveData<List<SelectedItemEntity>> = selectedItemRepository.likedSelectedItems()
    val isLikedItems: LiveData<List<SelectedItemEntity>> get() = _isLikedItems


    //검색된 데이터
    fun getSearchImageList(query: String) {
        viewModelScope.launch {
            _getSearchImageList.value = searchRepository.getSearchImageList(query).documents
        }
    }

    //마이데이터 저장
    fun saveSelectedItem(selectedItem: SearchItemEntity) {
        viewModelScope.launch {
            val thumbnailUrl = selectedItem.thumbnailUrl
            val isAlreadySelected = selectedItemRepository.isItemSelected(thumbnailUrl)
            if (isAlreadySelected) {
                // 이미 선택된 아이템일 경우 삭제
                val entity = SelectedItemEntity(
                    displaySiteName = selectedItem.displaySiteName,
                    datetime = selectedItem.datetime,
                    thumbnailUrl = thumbnailUrl,
                    isLiked = true)
                selectedItemRepository.deleteSelectedItem(entity)
            } else {
                // 선택되지 않은 아이템일 경우 저장
                val entity = SelectedItemEntity(
                    displaySiteName = selectedItem.displaySiteName,
                    datetime = selectedItem.datetime,
                    thumbnailUrl = thumbnailUrl,
                    isLiked = true
                )
                selectedItemRepository.insertSelectedItem(entity)
            }
        }
    }
    //todo 문제 1
    // SearchItemEntity -> SelectedItemEntity
    fun deleteSelectedItem(selectedItem: SearchItemEntity) {
        viewModelScope.launch {
            val entity = SelectedItemEntity(
                displaySiteName = selectedItem.displaySiteName,
                datetime = selectedItem.datetime,
                thumbnailUrl = selectedItem.thumbnailUrl,
                isLiked = true
            )
            selectedItemRepository.deleteSelectedItem(entity)
        }
    }
}

//todo SearchViewModelFactory 구현해보자!! -> 7강의
// hilt, koin 배워서 factory 지우기
//class SearchViewModelFactory(private val repository: SelectedItemRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
//            return SearchViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
class SearchViewModelFactory(private val searchRepository: SearchRepository, private val selectedItemRepository: SelectedItemRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(searchRepository, selectedItemRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}