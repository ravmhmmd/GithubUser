package com.cubing.githubuser

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cubing.githubuser.adapter.ListUserAdapter
import com.cubing.githubuser.databinding.FragmentFollowersFollowingBinding
import com.cubing.githubuser.model.User
import com.cubing.githubuser.viewmodel.UserFollowersViewModel

class FollowersFragment : Fragment(R.layout.fragment_followers_following) {

    private lateinit var followersViewModel : UserFollowersViewModel
    private lateinit var followersAdapter : ListUserAdapter
    private lateinit var userUsername : String

    private var fBinding : FragmentFollowersFollowingBinding? = null
    private val binding get() = fBinding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fBinding = FragmentFollowersFollowingBinding.bind(view)

        followersAdapter = ListUserAdapter()
        followersAdapter.notifyDataSetChanged()

        followersViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserFollowersViewModel::class.java)

        val tempArgs = arguments
        userUsername = tempArgs
            ?.getString(DetailPage.EXTRA_USERNAME)
            .toString()

        binding.apply {
            rvFollowerFollowing.layoutManager = LinearLayoutManager(activity)
            rvFollowerFollowing.adapter = followersAdapter
            rvFollowerFollowing.setHasFixedSize(true)
        }

        setShowLoading(true)

        followersViewModel.setListOfFollowers(userUsername)
        followersViewModel
            .getListOfFollowers()
            .observe(viewLifecycleOwner) {
                if (it != null) {
                    followersAdapter.setListOfUser(it)
                    setShowLoading(false)
                }
            }

        followersAdapter.setOnUserSelectCallback(object : ListUserAdapter.OnUserSelectCallback {
            override fun onUserSelected(dataUser: User) {
                val intent = Intent(activity, DetailPage::class.java)
                intent.putExtra(DetailPage.EXTRA_USERNAME, dataUser.username)
                intent.putExtra(DetailPage.EXTRA_ID, dataUser.id)
                intent.putExtra(DetailPage.EXTRA_AVATAR, dataUser.avatar)
                activity?.startActivity(intent)
            }
        })
    }

    private fun setShowLoading(state: Boolean) {
        if (state) {
            binding.pbFollowersFollowing.visibility = View.VISIBLE
        }
        else {
            binding.pbFollowersFollowing.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fBinding = null
    }

}