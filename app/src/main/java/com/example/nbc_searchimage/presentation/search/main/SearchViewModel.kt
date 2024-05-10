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
import com.example.nbc_searchimage.room.SelectedItemEntity
import kotlinx.coroutines.launch

class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel() {
    private val networkClient = RetrofitClient

    private val _getSearchImageList: MutableLiveData<List<SearchItemEntity>> = MutableLiveData()
    val getSearchImageList: LiveData<List<SearchItemEntity>> get() = _getSearchImageList


    // 좋아요 처리(room)
//    private val _isLikedItems: LiveData<List<SelectedItemEntity>> = repository.getAllSelectedItems()
//    val isLikedItems: LiveData<List<SelectedItemEntity>> get() = _isLikedItems


    //검색된 데이터
    fun getSearchImageList(query: String) {
        viewModelScope.launch {
            //viewModelScope ?
            // ViewModel이 destory될 때 자식 코루틴들을 자동으로 취소하는 기능을 제공
            _getSearchImageList.value = searchRepository.getSearchImageList(query).documents
        }
    }

    //마이데이터 저장
    fun saveSelectedItem(selectedItem: SearchItemEntity) {
        viewModelScope.launch {
            // Document에서 SelectedItemEntity로 변환하여 저장
            val entity = SelectedItemEntity(
                displaySiteName = selectedItem.displaySiteName,
                datetime = selectedItem.datetime,
                thumbnailUrl = selectedItem.thumbnailUrl,
                isLiked = true
            )
//            repository.insertSelectedItem(entity)
        }
    }
}

//todo SearchViewModelFactory 구현해보자!! -> 7강의
class SearchViewModelFactory : ViewModelProvider.Factory {
    private val repository = SearchRepositoryImpl(RetrofitClient.searchNetWork)
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return SearchViewModel(repository) as T
    }
}
//SelectedItemRepository 인스턴스를 받는다. Repository를 생성