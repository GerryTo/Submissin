package com.dicoding.submission2.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.submission2.UserResponseItem

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(userResponseItem: UserResponseItem)

    @Query("Delete from UserResponseItem WHERE id=:id")
    fun delete(id: Int)

    @Query("SELECT * from UserResponseItem ORDER BY id ASC")
    fun getAllFavorite(): LiveData<List<UserResponseItem>>


}