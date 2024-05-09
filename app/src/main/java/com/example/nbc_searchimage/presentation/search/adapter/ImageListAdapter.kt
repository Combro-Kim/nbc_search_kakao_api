package com.example.nbc_searchimage.presentation.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.nbc_searchimage.data.Document
import com.example.nbc_searchimage.databinding.ItemListBinding

class ImageListAdapter : ListAdapter<Document, ImageListViewHolder>(ImageDiffCallback()) {

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
        val isLiked = imageItem.thumbnail_url in likedItems
        holder.bind(imageItem,isLiked)
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it,position)
        }
    }

    // 기존 <-> 변경되는 데이터 확인
    private class ImageDiffCallback : DiffUtil.ItemCallback<Document>() {
        override fun areItemsTheSame(oldItem: Document, newItem: Document): Boolean {
            return oldItem.thumbnail_url == newItem.thumbnail_url
        }

        override fun areContentsTheSame(oldItem: Document, newItem: Document): Boolean {
            return oldItem == newItem
        }
    }
}
