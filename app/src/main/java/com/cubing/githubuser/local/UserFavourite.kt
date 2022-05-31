package com.cubing.githubuser.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "favourite_user")
data class UserFavourite(
    @SerializedName("login")
    var username: String,

    @PrimaryKey
    val id: Int,

    @SerializedName("avatar_url")
    val avatar: String,

    @SerializedName("name")
    val name: String?,

    @SerializedName("location")
    val location: String?,

    @SerializedName("public_repos")
    val repository: String?,

    @SerializedName("company")
    val company: String?,

    @SerializedName("followers")
    val followers: String?,

    @SerializedName("following")
    val following: String?,

    @SerializedName("followers_url")
    val followersUrl: String?,

    @SerializedName("following_url")
    val followingUrl: String?
) : Serializable
