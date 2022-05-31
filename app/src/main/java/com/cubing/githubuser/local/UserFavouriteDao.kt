package com.cubing.githubuser.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserFavouriteDao {

    @Insert
    fun addFavourite(userFavourite: UserFavourite)

    @Query("SELECT * FROM favourite_user")
    fun getUserFavourite() : LiveData<List<UserFavourite>>

    @Query("SELECT count(*) FROM favourite_user WHERE favourite_user.id = :id")
    fun checkUser(id : Int) : Int

    @Query("DELETE FROM favourite_user WHERE favourite_user.id = :id")
    fun deleteFavourite(id : Int) : Int

}