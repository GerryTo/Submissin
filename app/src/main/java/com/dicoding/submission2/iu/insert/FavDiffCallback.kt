package com.dicoding.submission2.iu.insert

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.submission2.UserResponseItem

class FavDiffCallback(private val mOldFavList: List<UserResponseItem>, private val mNewFavList: List<UserResponseItem>):
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldFavList.size
    }

    override fun getNewListSize(): Int {
        return mNewFavList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavList[oldItemPosition].id == mNewFavList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavList[oldItemPosition].login == mNewFavList[newItemPosition].login
    }
}