package com.cubing.githubuser

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cubing.githubuser.adapter.ListUserAdapter
import com.cubing.githubuser.databinding.ActivityMainBinding
import com.cubing.githubuser.model.User
import com.cubing.githubuser.settings.SettingPreferences
import com.cubing.githubuser.viewmodel.SettingViewModel
import com.cubing.githubuser.viewmodel.UserViewModel
import com.cubing.githubuser.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.Flow

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var listUserAdapter: ListUserAdapter

    private fun setShowLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        }
        else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun searchUser() {
        binding.apply {
            val sQuery = searchQuery.text.toString()
            if (sQuery.isEmpty()) return
            setShowLoading(true)
            userViewModel.setSearchedUser(sQuery)
        }
    }

    private fun initiateScreen() {
        binding.apply{
            setShowLoading(true)
            userViewModel.setSearchedUser("a")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        var isDarkMode = pref.getThemeSetting()
        val setViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )

        setViewModel.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        listUserAdapter = ListUserAdapter()
        listUserAdapter.notifyDataSetChanged()

        listUserAdapter.setOnUserSelectCallback(object: ListUserAdapter.OnUserSelectCallback{
            override fun onUserSelected(dataUser: User) {
                Intent(this@MainActivity, DetailPage::class.java)
                    .also {
                        it.putExtra(DetailPage.EXTRA_USERNAME, dataUser.username)
                        it.putExtra(DetailPage.EXTRA_ID, dataUser.id)
                        it.putExtra(DetailPage.EXTRA_AVATAR, dataUser.avatar)
                        startActivity(it)
                    }
            }

        })

        userViewModel = ViewModelProvider(
            this,ViewModelProvider
                .NewInstanceFactory()
        ).get(UserViewModel::class.java)

        initiateScreen()

        binding.apply {
            recyclerViewListUser.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerViewListUser.adapter = listUserAdapter
            recyclerViewListUser.setHasFixedSize(true)

            btnSearch.setOnClickListener {
                searchUser()
            }

            searchQuery.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        userViewModel.getSearchedUser().observe(this) {
            if (it != null) {
                listUserAdapter.setListOfUser(it)
                setShowLoading(false)
            }
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