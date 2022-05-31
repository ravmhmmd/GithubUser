package com.cubing.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cubing.githubuser.api.ApiClient
import com.cubing.githubuser.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserFollowersViewModel : ViewModel() {
    val listOfFollowers = MutableLiveData<ArrayList<User>>()

    fun getListOfFollowers() : LiveData<ArrayList<User>>{
        return listOfFollowers
    }

    fun setListOfFollowers(userUsername : String) {
        ApiClient
            .apiIns
            .getFollowersUser(userUsername)
            .enqueue(object : Callback<ArrayList<User>>{
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful){
                        listOfFollowers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {}

            })
    }
}