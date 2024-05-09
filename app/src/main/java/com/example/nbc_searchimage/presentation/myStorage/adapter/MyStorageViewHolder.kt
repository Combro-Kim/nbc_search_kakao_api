package com.example.nbc_searchimage.presentation.myStorage.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nbc_searchimage.databinding.ItemListBinding
import com.example.nbc_searchimage.room.SelectedItemEntity
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class MyStorageViewHolder(private val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(selectedItem: SelectedItemEntity){
        binding.apply {
            tvSiteName.text = selectedItem.displaySiteName

            val dateTime = OffsetDateTime.parse(selectedItem.datetime)
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            tvDate.text = dateTime.format(formatter)
            Glide.with(itemView.context)
                .load(selectedItem.thumbnailUrl)
                .into(ivThumbnail)
        }
    }
}