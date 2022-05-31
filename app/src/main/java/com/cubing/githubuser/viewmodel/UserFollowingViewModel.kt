package com.cubing.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cubing.githubuser.api.ApiClient
import com.cubing.githubuser.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserFollowingViewModel : ViewModel() {
    val listOfFollowing = MutableLiveData<ArrayList<User>>()

    fun getListOfFollowing() : LiveData<ArrayList<User>>{
        return listOfFollowing
    }

    fun setListOfFollowing(userUsername : String) {
        ApiClient
            .apiIns
            .getFollowingUser(userUsername)
            .enqueue(object : Callback<ArrayList<User>>{
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful){
                        listOfFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {}

            })
    }
}