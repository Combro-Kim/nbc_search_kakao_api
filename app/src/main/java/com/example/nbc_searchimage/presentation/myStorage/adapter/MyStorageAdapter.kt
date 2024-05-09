package com.example.nbc_searchimage.presentation.myStorage.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nbc_searchimage.databinding.ItemListBinding
import com.example.nbc_searchimage.room.SelectedItemEntity

class MyStorageAdapter : ListAdapter<SelectedItemEntity, MyStorageViewHolder>(StorageDiffCallback()) {

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyStorageViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyStorageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyStorageViewHolder, position: Int) {
        val imageItem = getItem(position)
        holder.bind(imageItem)
        holder.itemView.setOnClickListener {
            if (position != RecyclerView.NO_POSITION) {
                itemClick?.onClick(it, position)
                Log.d("선택한 아이템 포지션", "$position")
            }
        }
    }

    private class StorageDiffCallback : DiffUtil.ItemCallback<SelectedItemEntity>() {
        override fun areItemsTheSame(oldItem: SelectedItemEntity, newItem: SelectedItemEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SelectedItemEntity, newItem: SelectedItemEntity): Boolean {
            return oldItem == newItem
        }
    }
}

