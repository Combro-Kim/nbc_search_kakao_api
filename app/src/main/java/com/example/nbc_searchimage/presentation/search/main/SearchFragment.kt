package com.example.nbc_searchimage.presentation.search.main

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nbc_searchimage.data.SharedPreferencesManager
import com.example.nbc_searchimage.databinding.FragmentSearchBinding
import com.example.nbc_searchimage.network.RetrofitClient
import com.example.nbc_searchimage.presentation.search.adapter.ImageListAdapter
import com.example.nbc_searchimage.presentation.search.repository.SearchRepository
import com.example.nbc_searchimage.presentation.search.repository.SearchRepositoryImpl
import com.example.nbc_searchimage.room.MyDatabase
import com.example.nbc_searchimage.room.repository.SelectedItemRepository
import com.example.nbc_searchimage.room.repository.SelectedItemRepositoryImpl


class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SearchViewModel> {
        SearchViewModelFactory(
            searchRepository = SearchRepositoryImpl(RetrofitClient.searchNetWork),
            selectedItemRepository = SelectedItemRepositoryImpl(
                MyDatabase.getDatabase(requireContext()).selectedItemDao()
            )
        )
    }

    private lateinit var adapter: ImageListAdapter
    private var likedItems: MutableSet<String> = mutableSetOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initView()
        initViewModel()
        setSearchButtonOnClickListener()
        scrollUpEvent()
    }

    private fun initView() {
        //검색어 show
        val setSearchWord = SharedPreferencesManager.setSearchWord(requireContext())
        binding.etSearch.setText(setSearchWord)
        if (!setSearchWord.isNullOrEmpty()) {
            viewModel.getSearchImageList(setSearchWord)
        }

//        //검색 결과 리스트 show
//        val setSearchResultList = SharedPreferencesManager.setSearchResult(requireContext())
//        setSearchResultList?.let {
//            adapter.submitList(setSearchResultList)
//        }
    }

    private fun initRecyclerView() {
        adapter = ImageListAdapter()
        binding.searchRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.searchRecyclerView.adapter = adapter

        adapter.itemClick = object : ImageListAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val selectedItem = adapter.currentList[position]
                if (selectedItem.isLiked) {
                    viewModel.deleteSelectedItem(selectedItem)
                    Log.d("eeeeeee","$selectedItem")
                } else {
                    viewModel.saveSelectedItem(selectedItem)
                }
                selectedItem.isLiked = !selectedItem.isLiked
                adapter.notifyItemChanged(position)
            }
        }

        // 아이템 간격 주기
        binding.searchRecyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.bottom = 40
            }
        })
    }

    private fun initViewModel() {
        viewModel.getSearchImageList.observe(viewLifecycleOwner) { images ->
            adapter.submitList(images)
        }
        viewModel.isLikedItems.observe(viewLifecycleOwner) { items ->
            likedItems = items.map { it.thumbnailUrl }.toMutableSet() // 좋아요된 이미지들의 URL을 업데이트
            adapter.likedItems = likedItems // 어댑터에 좋아요된 이미지들의 URL 집합 전달
        }
    }

    //검색
    private fun setSearchButtonOnClickListener() {
        binding.btnSearch.setOnClickListener {
            hideKeyboard()
            val searchWord = binding.etSearch.text.toString()
            viewModel.getSearchImageList(searchWord)
            SharedPreferencesManager.getSearchWord(requireContext(), searchWord) //검색어 저장
        }
    }

    //검색버튼 클릭 -> 키보드 내림
    private fun hideKeyboard() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.btnSearch.windowToken, 0)
    }

    //스크롤 최상단 올리기
    private fun scrollUpEvent() {
        val btnUp = binding.btnUp
        //fadeIn, fadeOut 투명 애니메이션
        val fadeIn = AlphaAnimation(0f, 1f).apply { duration = 300 }
        val fadeOut = AlphaAnimation(1f, 0f).apply { duration = 300 }

        //스크롤 이벤트 감지
        binding.searchRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(
                recyclerView: RecyclerView,
                newState: Int
            ) { //OnScrolled vs onScrollStateChange
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) { // SCROLL_STATE_SETTLING, SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING
                    if (!recyclerView.canScrollVertically(-1)) { //최상단일 경우 -1 <> 최하단일 경우 0
                        btnUp.startAnimation(fadeOut)
                        btnUp.visibility = View.GONE
                    } else {
                        if (btnUp.visibility == View.GONE) {
                            btnUp.startAnimation(fadeIn)
                        }
                        btnUp.visibility = View.VISIBLE
                    }
                }
            }
        })
        btnUp.setOnClickListener {//최상단으로 이동
            binding.searchRecyclerView.smoothScrollToPosition(0)
        }
    }

    //binding null처리(메모리 누수)
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}