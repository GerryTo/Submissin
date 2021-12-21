package com.dicoding.submission2.iu.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission2.R
import com.dicoding.submission2.UserResponseItem
import com.dicoding.submission2.adapter.UserAdapter
import com.dicoding.submission2.databinding.ActivityMainBinding
import com.dicoding.submission2.settings.Settings
import com.dicoding.submission2.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager


        mainViewModel.userResponseItem.observe(this, { user ->
            showListUser(user)
        })

        mainViewModel.isLoading.observe(this,{
            showLoading(it)
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading){
            binding.spinKit.visibility = View.VISIBLE
        }
        else{
            binding.spinKit.visibility = View.GONE
        }
    }

    private fun showListUser(user : List<UserResponseItem>) {
        val adapter= UserAdapter()
        binding.rvUser.adapter= adapter
        adapter.setListFav(user)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                mainViewModel.searchUsername(newText)
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.setting ->{
                val intent = Intent(this, Settings::class.java)
                startActivity(intent)
                return true
            }
            R.id.Favorite ->{
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return true
        }
    }
}

