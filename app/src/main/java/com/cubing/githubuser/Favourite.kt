package com.cubing.githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cubing.githubuser.adapter.ListUserAdapter
import com.cubing.githubuser.databinding.ActivityFavouriteBinding
import com.cubing.githubuser.local.UserFavourite
import com.cubing.githubuser.model.User
import com.cubing.githubuser.viewmodel.UserFavouriteViewModel

class Favourite : AppCompatActivity() {

    private lateinit var binding: ActivityFavouriteBinding
    private lateinit var favAdapter: ListUserAdapter
    private lateinit var favViewModel: UserFavouriteViewModel

    private fun mapList(userList: List<UserFavourite>): ArrayList<User> {
        val list = ArrayList<User>()
        for (user in userList) {
            val mappedUser = User(
                user.id,
                user.username,
                user.avatar,
                user.name,
                user.location,
                user.repository,
                user.company,
                user.followers,
                user.following,
                user.followersUrl,
                user.followingUrl
            )
            list.add(mappedUser)
        }
        return list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favViewModel = ViewModelProvider(this).get(UserFavouriteViewModel::class.java)

        favViewModel.getUserFavourite()?.observe(this) {
            if (it != null) {
                val listFavUser = mapList(it)
                favAdapter.setListOfUser(listFavUser)
            }
        }

        favAdapter = ListUserAdapter()
        favAdapter.notifyDataSetChanged()

        favAdapter.setOnUserSelectCallback(object : ListUserAdapter.OnUserSelectCallback {
            override fun onUserSelected(dataUser: User) {
                Intent(this@Favourite, DetailPage:: class.java).also {
                    it.putExtra(DetailPage.EXTRA_USERNAME, dataUser.username)
                    it.putExtra(DetailPage.EXTRA_ID, dataUser.id)
                    it.putExtra(DetailPage.EXTRA_AVATAR, dataUser.avatar)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvFavouriteUser.layoutManager = LinearLayoutManager(this@Favourite)
            rvFavouriteUser.adapter = favAdapter
            rvFavouriteUser.setHasFixedSize(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.main_menu -> {
                Intent(this, MainActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.fav_menu -> {
                Intent(this, Favourite::class.java).also {
                    startActivity(it)
                }
            }
            R.id.settings_menu -> {
                Intent(this, Settings::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}