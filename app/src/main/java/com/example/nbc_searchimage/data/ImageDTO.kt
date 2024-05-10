package com.example.nbc_searchimage.data

import com.google.gson.annotations.SerializedName

data class DocumentResponse(
    @SerializedName("documents") val documents: List<Document>
)


data class Document(
    @SerializedName("datetime") val datetime: String,
    @SerializedName("display_sitename") val displaySiteName: String,
    @SerializedName("thumbnail_url") val thumbnailUrl: String,
)
