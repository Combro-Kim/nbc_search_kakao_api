package com.example.nbc_searchimage.data

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    val meta: Meta,
    val documents: MutableList<Document>?
)

data class Meta(
    val total_count: Int,
    val pageable_count: Int,
    val is_end: Boolean
)

data class Document(
    val datetime: String,
    val display_sitename: String,
    val thumbnail_url: String,
)
