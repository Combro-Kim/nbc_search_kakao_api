package com.example.nbc_searchimage.presentation.search.repository

import com.example.nbc_searchimage.data.remote.DocumentResponse
import com.example.nbc_searchimage.data.remote.SearchRemoteDataSource
import com.example.nbc_searchimage.presentation.search.mapper.toEntity
import com.example.nbc_searchimage.presentation.search.model.SearchItemListEntity
import com.example.nbc_searchimage.presentation.search.repository.SearchRepository

class SearchRepositoryImpl(private val remoteDataSource: SearchRemoteDataSource): SearchRepository {

    override suspend fun getSearchImageList(query: String): SearchItemListEntity =
        remoteDataSource.getDocumentResponse(query).toEntity()

}