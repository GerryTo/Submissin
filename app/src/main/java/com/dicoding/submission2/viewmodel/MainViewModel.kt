package com.dicoding.submission2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submission2.api.ApiConfig
import com.dicoding.submission2.SearchUsername
import com.dicoding.submission2.UserResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _userResponseItem = MutableLiveData<ArrayList<UserResponseItem>>()
    val userResponseItem: LiveData<ArrayList<UserResponseItem>> = _userResponseItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init{
        findUser()
    }

    private fun findUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListUser()
        client.enqueue(object : Callback<ArrayList<UserResponseItem>> {
            override fun onResponse(
                call: Call<ArrayList<UserResponseItem>>,
                response: Response<ArrayList<UserResponseItem>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful)_userResponseItem.value = response.body()
                else Log.e(TAG,"onFailure:${response.errorBody()?.string()}")
            }

            override fun onFailure(call: Call<ArrayList<UserResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG,"onFailure:${t.message}")
            }
        })
    }

    fun searchUsername(query: String){
        _isLoading.value = true
        if(query.isEmpty()){
            _isLoading.value = false
            findUser()
        }
        else{
            val client = ApiConfig.getApiService().searchUser(query)
            client.enqueue(object : Callback<SearchUsername>{
                override fun onResponse(
                    call: Call<SearchUsername>,
                    response: Response<SearchUsername>
                ) {
                    _isLoading.value = false
                    if(response.isSuccessful)_userResponseItem.postValue(response.body()?.item)
                    else Log.e(TAG,"onFailure:${response.errorBody()?.string()}")
                }

                override fun onFailure(call: Call<SearchUsername>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG,"onFailure:${t.message}")
                }
            })
        }
    }

    companion object{
        private const val TAG = "MainViewModel"
    }
}