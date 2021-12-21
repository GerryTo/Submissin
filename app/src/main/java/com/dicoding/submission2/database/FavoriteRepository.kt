package com.dicoding.submission2.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.submission2.UserResponseItem
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoritesDao: FavoriteDao
    private val executorService :ExecutorService = Executors.newSingleThreadExecutor()

    init{
        val db = FavRoomDatabase.getDatabase(application)
        mFavoritesDao = db.favoriteDao()
    }

    fun getAllFavorite(): LiveData<List<UserResponseItem>> = mFavoritesDao.getAllFavorite()

    fun insert(userResponseItem: UserResponseItem){
        executorService.execute{mFavoritesDao.insert(userResponseItem)}
    }

    fun delete(id:Int){
        executorService.execute{mFavoritesDao.delete(id)}
    }
}