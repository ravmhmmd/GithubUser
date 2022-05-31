package com.cubing.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cubing.githubuser.api.ApiClient
import com.cubing.githubuser.local.UserDatabase
import com.cubing.githubuser.local.UserFavourite
import com.cubing.githubuser.local.UserFavouriteDao
import com.cubing.githubuser.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel(application: Application) : AndroidViewModel(application) {

    val detailUserData = MutableLiveData<User>()

    private var userDao: UserFavouriteDao?
    private var userDatabase: UserDatabase?

    init {
        userDatabase = UserDatabase.getDatabase(application)
        userDao = userDatabase?.userFavouriteDao()
    }

    fun addFavourite(username: String, id: Int, avatar: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var user = UserFavourite(
                username, id, avatar, null, null, null, null, null, null, null, null
            )
            userDao?.addFavourite(user)
        }
    }

    fun checkUser(id: Int): Int? {
        return userDao?.checkUser(id)
    }

    fun deleteFavourite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.deleteFavourite(id)
        }
    }

    fun getDetailUserData(): LiveData<User> {
        return detailUserData
    }

    fun setDetailUserData(username: String) {
        ApiClient.apiIns
            .getDetailUser(username)
            .enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        detailUserData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {}

            })
    }
}