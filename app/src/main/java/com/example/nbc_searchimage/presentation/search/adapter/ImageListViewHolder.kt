package com.example.nbc_searchimage.presentation.search.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nbc_searchimage.databinding.ItemListBinding
import com.example.nbc_searchimage.presentation.search.model.SearchItemEntity
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class ImageListViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(imageItem: SearchItemEntity, isLiked: Boolean) {
        binding.apply {
//            tvSiteName.text = imageItem.display_sitename
            tvSiteName.text = imageItem.displaySiteName
            val dateTime = OffsetDateTime.parse(imageItem.datetime)
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            tvDate.text = dateTime.format(formatter)
            Glide.with(itemView.context)
                .load(imageItem.thumbnailUrl)
                .into(ivThumbnail)

            ivHeart.visibility = if (isLiked) View.VISIBLE else View.GONE
        }
    }
}