package com.example.nbc_searchimage.presentation.search.repository

import com.example.nbc_searchimage.presentation.search.model.SearchItemListEntity

interface SearchRepository {
    suspend fun getSearchImageList(userName: String) : SearchItemListEntity
}