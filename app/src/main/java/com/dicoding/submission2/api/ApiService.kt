package com.dicoding.submission2.api

import com.dicoding.submission2.BuildConfig
import com.dicoding.submission2.SearchUsername
import com.dicoding.submission2.UserDetail
import com.dicoding.submission2.UserResponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/users")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getListUser(): Call<ArrayList<UserResponseItem>>

    @GET("/search/users")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun searchUser(
        @Query("q") username:String
    ): Call<SearchUsername>

    @GET("/users/{username}")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getDetail(
        @Path("username") username:String
    ):Call<UserDetail>

    @GET("/users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getListFollower(
        @Path("username") username:String
    ): Call<ArrayList<UserResponseItem>>

    @GET("/users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getListFollowing(
        @Path("username") username:String
    ): Call<ArrayList<UserResponseItem>>
}