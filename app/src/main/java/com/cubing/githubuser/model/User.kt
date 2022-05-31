package com.cubing.githubuser.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id : Int?,

    @SerializedName("login")
    var username : String?,

    @SerializedName("avatar_url")
    val avatar : String?,

    @SerializedName("name")
    val name : String?,

    @SerializedName("location")
    val location : String?,

    @SerializedName("public_repos")
    val repository : String?,

    @SerializedName("company")
    val company : String?,

    @SerializedName("followers")
    val followers : String?,

    @SerializedName("following")
    val following : String?,

    @SerializedName("followers_url")
    val followersUrl : String?,

    @SerializedName("following_url")
    val followingUrl : String?
)
