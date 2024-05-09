package com.example.nbc_searchimage.room

import androidx.room.*

@Entity(tableName = "selected_items")
data class SelectedItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val displaySiteName: String,
    val datetime: String,
    val thumbnailUrl: String,
    var isLiked: Boolean = false
)