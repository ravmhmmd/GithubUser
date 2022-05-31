package com.cubing.githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.cubing.githubuser.adapter.SectionsPagerAdapter
import com.cubing.githubuser.databinding.ActivityDetailPageBinding
import com.cubing.githubuser.viewmodel.UserDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailPage : AppCompatActivity() {

    private var dBinding : ActivityDetailPageBinding? = null
    private val binding get() = dBinding!!
    private lateinit var detailPageViewModel: UserDetailViewModel

    private fun setShowLoading(state: Boolean) {
        if (state) {
            binding.pbDetailPage.visibility = View.VISIBLE
            binding.detailConstraintLayout.visibility = View.INVISIBLE
        }
        else {
            binding.pbDetailPage.visibility = View.GONE
            binding.detailConstraintLayout.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dBinding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dBinding = ActivityDetailPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailPageViewModel = ViewModelProvider(this)
            .get(UserDetailViewModel::class.java)

        val bundleForFragment = Bundle()

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID,0)
        val avatar = intent.getStringExtra(EXTRA_AVATAR)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, bundleForFragment)

        setShowLoading(true)

        detailPageViewModel.setDetailUserData(username.toString())
        detailPageViewModel.getDetailUserData().observe(this) {
            if (it != null) {
                binding.apply {

                    pcUsername.text = it.username

                    if (it.name != null) {
                        pcName.text = it.name
                    } else if (it.name == null){
                        pcName.text = it.username
                        pcUsername.visibility = View.GONE
                    }

                    if (it.company != null) {
                        pcCompany.text = " " + it.company
                    } else if (it.company == null){
                        pcCompany.text = " - "
                    }

                    if (it.location != null) {
                        pcLocation.text = " " + it.location
                    } else if (it.location == null){
                        pcLocation.text = " - "
                    }

//                    pcName.text = it.name
//                    pcCompany.text = it.company
//                    pcLocation.text = it.location
                    pcFollower.text = it.followers
                    pcFollowing.text = it.following
                    pcRepository.text = it.repository

                    Glide
                        .with(this@DetailPage)
                        .load(it.avatar)
                        .centerCrop()
                        .into(pcAvatar)

                    setShowLoading(false)
                }
            }
        }

        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = detailPageViewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null) {
                    if (count>0) {
                        binding.favToggle.isChecked = true
                        isChecked = true
                    }
                    else {
                        binding.favToggle.isChecked = false
                        isChecked = false
                    }
                }
            }
        }

        binding.apply {
            viewPager.adapter = sectionsPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }


        binding.favToggle.setOnClickListener {
            isChecked = !isChecked
            if (isChecked) {
                detailPageViewModel.addFavourite(username!!, id, avatar!!)
            }
            else {
                detailPageViewModel.deleteFavourite(id)
            }
            binding.favToggle.isChecked = isChecked
        }

        bundleForFragment.putString(EXTRA_USERNAME, username)

    }

    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"
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