package com.cubing.githubuser.adapter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.cubing.githubuser.FollowersFragment
import com.cubing.githubuser.FollowingFragment
import com.cubing.githubuser.R

class SectionsPagerAdapter(
    private val mContext: Context,
    fragmentManager: FragmentManager,
    dataUsername : Bundle
) : FragmentPagerAdapter(
    fragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    private val tabsName = intArrayOf(
        R.string.followers_placeholder,
        R.string.following_placeholder)

    private var bundleFragment : Bundle

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext
            .resources
            .getString(tabsName[position])
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }

        fragment?.arguments = this.bundleFragment

        return fragment as Fragment
    }

    init {
        bundleFragment = dataUsername
    }

}