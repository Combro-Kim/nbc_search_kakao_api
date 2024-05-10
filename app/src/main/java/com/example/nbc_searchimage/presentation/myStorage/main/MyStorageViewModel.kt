package com.example.nbc_searchimage.presentation.myStorage.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.nbc_searchimage.room.SelectedItemEntity
import com.example.nbc_searchimage.room.repository.SelectedItemRepository
import kotlinx.coroutines.launch

class MyStorageViewModel(private val repository: SelectedItemRepository) : ViewModel() {

    private val _selectedItems: LiveData<List<SelectedItemEntity>> = repository.likedSelectedItems()
    val selectedItems: LiveData<List<SelectedItemEntity>> get() = _selectedItems

    fun deleteSelectedItem(selectedItem: SelectedItemEntity) {
        viewModelScope.launch {
            repository.deleteSelectedItem(selectedItem)
        }
    }
}

class MyStorageViewModelFactory( private val selectedItemRepository: SelectedItemRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyStorageViewModel::class.java)) {
            return MyStorageViewModel(selectedItemRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
//class MyStorageViewModelFactory : ViewModelProvider.Factory {
//    private val repository = SelectedItemRepositoryImpl(DataSource, RetrofitClient.searchGitHubUser)
//    override fun <T : ViewModel> create(
//        modelClass: Class<T>,
//        extras: CreationExtras
//    ): T {
//        return GitHubUserViewModel(
//            repository
//        ) as T
//    }
//}