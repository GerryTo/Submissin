package com.dicoding.submission2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.submission2.UserResponseItem

@Database(entities = [UserResponseItem::class], version = 1)
abstract class FavRoomDatabase: RoomDatabase() {
    abstract fun favoriteDao() : FavoriteDao

    companion object{
        @Volatile
        private var INSTANCE: FavRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavRoomDatabase{
            if(INSTANCE == null){
                synchronized(FavRoomDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    FavRoomDatabase::class.java,"Favorite_database")
                        .build()
                }
            }
            return INSTANCE as FavRoomDatabase
        }
    }
}