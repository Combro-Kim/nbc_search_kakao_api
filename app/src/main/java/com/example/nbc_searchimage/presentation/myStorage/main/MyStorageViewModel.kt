package com.example.nbc_searchimage.presentation.myStorage.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.nbc_searchimage.room.SelectedItemEntity
import com.example.nbc_searchimage.room.SelectedItemRepository
import kotlinx.coroutines.launch

class MyStorageViewModel(private val repository: SelectedItemRepository) : ViewModel() {

    private val _selectedItems: LiveData<List<SelectedItemEntity>> = repository.getAllSelectedItems()
    val selectedItems: LiveData<List<SelectedItemEntity>> get() = _selectedItems

    fun deleteSelectedItem(selectedItem: SelectedItemEntity) {
        viewModelScope.launch {
            repository.deleteSelectedItem(selectedItem)
        }
    }
}

class MyStorageViewModelFactory(private val repository: SelectedItemRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyStorageViewModel::class.java)) {
            return MyStorageViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}