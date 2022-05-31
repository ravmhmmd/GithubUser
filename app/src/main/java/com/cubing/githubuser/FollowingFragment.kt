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
import com.cubing.githubuser.viewmodel.UserFollowingViewModel

class FollowingFragment : Fragment(R.layout.fragment_followers_following) {

    private lateinit var followingViewModel : UserFollowingViewModel
    private lateinit var followingAdapter : ListUserAdapter
    private lateinit var userUsername : String

    private var fBinding : FragmentFollowersFollowingBinding? = null
    private val binding get() = fBinding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fBinding = FragmentFollowersFollowingBinding.bind(view)

        followingAdapter = ListUserAdapter()
        followingAdapter.notifyDataSetChanged()

        followingViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserFollowingViewModel::class.java)

        val tempArgs = arguments
        userUsername = tempArgs
            ?.getString(DetailPage.EXTRA_USERNAME)
            .toString()

        binding.apply {
            rvFollowerFollowing.layoutManager = LinearLayoutManager(activity)
            rvFollowerFollowing.adapter = followingAdapter
            rvFollowerFollowing.setHasFixedSize(true)
        }

        setShowLoading(true)

        followingViewModel.setListOfFollowing(userUsername)
        followingViewModel
            .getListOfFollowing()
            .observe(viewLifecycleOwner) {
                if (it != null) {
                    followingAdapter.setListOfUser(it)
                    setShowLoading(false)
                }
            }

        followingAdapter.setOnUserSelectCallback(object : ListUserAdapter.OnUserSelectCallback {
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