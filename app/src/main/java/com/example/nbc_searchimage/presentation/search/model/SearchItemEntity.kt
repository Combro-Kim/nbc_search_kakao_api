package com.example.nbc_searchimage.presentation.search.model


data class SearchItemListEntity(
    val documents: List<SearchItemEntity>
)

data class SearchItemEntity(
    val datetime: String,
    val displaySiteName: String,
    val thumbnailUrl: String,
)
