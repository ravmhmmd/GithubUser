package com.cubing.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cubing.githubuser.api.ApiClient
import com.cubing.githubuser.model.User
import com.cubing.githubuser.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {

    val listUser = MutableLiveData<ArrayList<User>>()

    fun getSearchedUser() : LiveData<ArrayList<User>> {
        return listUser
    }

    fun setSearchedUser(query : String){
        ApiClient.apiIns.getSearchedUser(query).enqueue(object : Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        listUser.postValue(response.body()?.user)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {}
            })
    }
}