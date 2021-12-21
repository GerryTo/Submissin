package com.dicoding.submission2.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submission2.api.ApiConfig
import com.dicoding.submission2.UserDetail
import com.dicoding.submission2.UserResponseItem
import com.dicoding.submission2.database.FavoriteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): ViewModel() {

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    private val _detailUser = MutableLiveData<UserDetail>()
    val detailUser: LiveData<UserDetail> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun showDetailUser(text: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetail(text)
        client.enqueue(object : Callback<UserDetail> {

            override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                _isLoading.value = false
                if(response.isSuccessful) _detailUser.postValue(response.body())
                else Log.e(TAG,"onFailure:${response.errorBody()?.string()}")
            }

            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun insert(userResponseItem: UserResponseItem?){
        if (userResponseItem != null) {
            mFavoriteRepository.insert(userResponseItem)
        }
    }

    fun delete(id:Int?){
        if (id != null) {
            mFavoriteRepository.delete(id)
        }
    }
    fun getAllFavorite():LiveData<List<UserResponseItem>> = mFavoriteRepository.getAllFavorite()

    companion object{
        const val TAG = "DetailUser"
    }
}