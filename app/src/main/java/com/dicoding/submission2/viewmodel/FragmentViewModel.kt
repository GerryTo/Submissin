package com.dicoding.submission2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submission2.api.ApiConfig
import com.dicoding.submission2.UserResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentViewModel: ViewModel() {


    private val _userResponseItem = MutableLiveData<ArrayList<UserResponseItem>>()
    val userResponseItem: LiveData<ArrayList<UserResponseItem>> = _userResponseItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun showFollower(query:String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListFollower(query)
        client.enqueue(object : Callback<ArrayList<UserResponseItem>> {

            override fun onResponse(
                call: Call<ArrayList<UserResponseItem>>,
                response: Response<ArrayList<UserResponseItem>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) _userResponseItem.value = response.body()
                else Log.e(DetailViewModel.TAG,"onFailure:${response.errorBody()?.string()}")
            }

            override fun onFailure(call: Call<ArrayList<UserResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(DetailViewModel.TAG, "onFailure:${t.message}")
            }
        })
    }

    fun showFollowing(query:String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListFollowing(query)
        client.enqueue(object : Callback<ArrayList<UserResponseItem>> {

            override fun onResponse(
                call: Call<ArrayList<UserResponseItem>>,
                response: Response<ArrayList<UserResponseItem>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful)_userResponseItem.value = response.body()
                else Log.e(DetailViewModel.TAG,"onFailure:${response.errorBody()?.string()}")
            }

            override fun onFailure(call: Call<ArrayList<UserResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(DetailViewModel.TAG, "onFailure:${t.message}")
            }
        })
    }
}