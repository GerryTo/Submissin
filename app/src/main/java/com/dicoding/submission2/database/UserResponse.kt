package com.dicoding.submission2

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity
data class UserResponseItem(
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	@field:SerializedName("id")
	var id: Int = 0,

	@ColumnInfo(name = "username")
	@field:SerializedName("login")
	var login: String? = null,

	@ColumnInfo(name = "avatar_url")
	@field:SerializedName("avatar_url")
	var avatarUrl: String

) : Parcelable

data class SearchUsername(
	@field:SerializedName("items")
	val item: ArrayList<UserResponseItem>
)

data class UserDetail(
	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("company")
	val company: String,

	@field:SerializedName("location")
	val location: String,

	@field:SerializedName("public_repos")
	val repository: Int,

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("following")
	val following: Int

)
