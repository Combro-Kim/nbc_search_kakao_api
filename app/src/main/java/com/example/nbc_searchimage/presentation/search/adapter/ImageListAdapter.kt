package com.example.nbc_searchimage.presentation.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.nbc_searchimage.databinding.ItemListBinding
import com.example.nbc_searchimage.presentation.search.model.SearchItemEntity

class ImageListAdapter : ListAdapter<SearchItemEntity, ImageListViewHolder>(ImageDiffCallback()) {

    interface ItemClick{
        fun onClick(view: View, position: Int)
    }
    var itemClick : ItemClick? = null
    var likedItems: Set<String> = emptySet()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageListViewHolder, position: Int) {
        val imageItem = getItem(position)
        val isLiked = imageItem.thumbnailUrl in likedItems
        holder.bind(imageItem,isLiked)
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it,position)
        }
    }

    // 기존 <-> 변경되는 데이터 비교
    private class ImageDiffCallback : DiffUtil.ItemCallback<SearchItemEntity>() {
        override fun areItemsTheSame(oldItem: SearchItemEntity, newItem: SearchItemEntity): Boolean {
            return oldItem.thumbnailUrl == newItem.thumbnailUrl
        }

        override fun areContentsTheSame(oldItem: SearchItemEntity, newItem: SearchItemEntity): Boolean {
            return oldItem == newItem
        }
    }
}
