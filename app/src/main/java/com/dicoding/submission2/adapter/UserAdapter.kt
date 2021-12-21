package com.dicoding.submission2.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submission2.iu.main.DetailUser
import com.dicoding.submission2.iu.insert.FavDiffCallback
import com.dicoding.submission2.UserResponseItem
import com.dicoding.submission2.databinding.ListDataBinding

class UserAdapter:RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private val listData = ArrayList<UserResponseItem>()

    fun setListFav(listUser: List<UserResponseItem>) {
        val diffCallback = FavDiffCallback(this.listData, listUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listData.clear()
        this.listData.addAll(listUser)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(private val binding: ListDataBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(user:UserResponseItem){
            with(binding){
                tvUsername.text = user.login
                Glide.with(binding.listItem)
                    .load(user.avatarUrl)
                    .into(imgPhoto)
                listItem.setOnClickListener{
                    val intent = Intent(it.context, DetailUser::class.java)
                    intent.putExtra(DetailUser.EXTRA_USER, user.login)
                    it.context.startActivity(intent)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount()=listData.size
}