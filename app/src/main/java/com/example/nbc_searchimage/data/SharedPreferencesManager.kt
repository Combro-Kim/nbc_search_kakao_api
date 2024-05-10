package com.example.nbc_searchimage.data

import android.content.Context
import android.content.SharedPreferences
import com.example.nbc_searchimage.room.SelectedItemEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SharedPreferencesManager {

    private const val PREF_NAME = "SearchPref" //파일 이름
    private const val SEARCH_WORD = "last_search_query" //마지막 검색_키워드
    private const val SEARCH_LIST = "last_search_result" //마지막 검색_결과

    //초기화
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
    //검색 키워드 저장
    fun getSearchWord(context: Context, query: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(SEARCH_WORD, query)
        editor.apply()
    }

    fun setSearchWord(context: Context): String? {
        return getSharedPreferences(context).getString(SEARCH_WORD, null)
    }
}