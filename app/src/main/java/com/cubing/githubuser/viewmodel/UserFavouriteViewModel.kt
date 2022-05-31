package com.cubing.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.cubing.githubuser.local.UserDatabase
import com.cubing.githubuser.local.UserFavourite
import com.cubing.githubuser.local.UserFavouriteDao

class UserFavouriteViewModel(application: Application) : AndroidViewModel(application) {
    private var userDao: UserFavouriteDao?
    private var userDatabase: UserDatabase?

    init {
        userDatabase = UserDatabase.getDatabase(application)
        userDao = userDatabase?.userFavouriteDao()
    }

    fun getUserFavourite(): LiveData<List<UserFavourite>>? {
        return userDao?.getUserFavourite()
    }
}