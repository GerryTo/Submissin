package com.dicoding.submission2.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submission2.UserResponseItem
import com.dicoding.submission2.database.FavoriteRepository

class FavoriteViewModel(application: Application):ViewModel() {

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFavorite():LiveData<List<UserResponseItem>> {
        return mFavoriteRepository.getAllFavorite()
    }

}