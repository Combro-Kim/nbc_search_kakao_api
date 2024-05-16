package com.example.nbc_searchimage.presentation.search.mapper

import com.example.nbc_searchimage.data.remote.Document
import com.example.nbc_searchimage.data.remote.DocumentResponse
import com.example.nbc_searchimage.presentation.search.model.SearchItemEntity
import com.example.nbc_searchimage.presentation.search.model.SearchItemListEntity

fun DocumentResponse.toEntity() = SearchItemListEntity(
    documents = documents.asSearchImageEntity()
)

fun List<Document>.asSearchImageEntity(): List<SearchItemEntity> {
    return map {
        SearchItemEntity(
            it.datetime,
            it.displaySiteName,
            it.thumbnailUrl
        )
    }
}
//sp 선언내용 레파지토리로 이동