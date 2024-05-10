package com.example.nbc_searchimage.presentation.myStorage.main

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nbc_searchimage.databinding.FragmentMystorageBinding
import com.example.nbc_searchimage.presentation.myStorage.adapter.MyStorageAdapter
import com.example.nbc_searchimage.room.MyDatabase
import com.example.nbc_searchimage.room.repository.SelectedItemRepositoryImpl


class MyStorageFragment : Fragment() {
    private var _binding: FragmentMystorageBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MyStorageAdapter
    private val viewModel by viewModels<MyStorageViewModel> {
        MyStorageViewModelFactory(SelectedItemRepositoryImpl(MyDatabase.getDatabase(requireContext()).selectedItemDao()))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMystorageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeViewModel()
    }


    private fun initRecyclerView() {
        adapter = MyStorageAdapter()
        binding.myStorageRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.myStorageRecyclerView.adapter = adapter

        adapter.itemClick = object : MyStorageAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                //room 데이터 삭제
                if (position >= 0 && position < adapter.itemCount) {
                    val selectedItem = adapter.currentList[position]
                    viewModel.deleteSelectedItem(selectedItem)

                    viewModel.selectedItems.observe(viewLifecycleOwner) { items ->
                        // 삭제한 아이템 뒤의 데이터들의 position 값 -1씩
                        val updatedItems = items.mapIndexed { index, item ->
                            if (index > position) {
                                item.copy(id = item.id - 1)
                            } else {
                                item
                            }
                        }
                        adapter.submitList(updatedItems)
                    }
                }
            }
        }

        // 아이템 간격 주기
        binding.myStorageRecyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.bottom = 40
            }
        })
    }

    private fun observeViewModel() {
        viewModel.selectedItems.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}