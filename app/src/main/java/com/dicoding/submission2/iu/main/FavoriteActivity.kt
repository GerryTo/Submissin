package com.dicoding.submission2.iu.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission2.UserResponseItem
import com.dicoding.submission2.adapter.UserAdapter
import com.dicoding.submission2.viewmodel.FavoriteViewModel
import com.dicoding.submission2.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser1.layoutManager = layoutManager

        favoriteViewModel = obtainViewModel(this@FavoriteActivity)
        favoriteViewModel.getAllFavorite().observe(this, { listFav->
            showListUser(listFav)
        })
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel{
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    private fun showListUser(user : List<UserResponseItem>) {
        val adapter= UserAdapter()
        binding.rvUser1.adapter= adapter
        if(user!=null){
            adapter.setListFav(user)
        }
    }
}