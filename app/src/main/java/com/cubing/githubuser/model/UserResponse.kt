package com.cubing.githubuser.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName ("items")
    val user : ArrayList<User>
)
