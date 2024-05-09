package com.example.nbc_searchimage.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nbc_searchimage.R
import com.example.nbc_searchimage.databinding.ActivityMainBinding
import com.example.nbc_searchimage.presentation.myStorage.main.MyStorageFragment
import com.example.nbc_searchimage.presentation.search.main.SearchFragment


class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initFirstFragment()
        setUpBottomNavigation()
    }

    private fun initFirstFragment(){
        //메인화면은 SearchFragment
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, SearchFragment())
            commit()
        }
    }

    private fun setUpBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.searchFragment -> {
                    val searchFragment = SearchFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, searchFragment)
                        .commit()
                    true
                }

                R.id.myStorageFragment -> {
                    val myStorageFragment = MyStorageFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, myStorageFragment)
                        .commit()
                    true
                }
                else -> false
            }
        }
    }


}