package com.dicoding.submission2.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.submission2.fragment.FollowerFragment
import com.dicoding.submission2.fragment.FollowingFragment

class SectionPager(val username: String, activity:  AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment : Fragment? = null
        when(position){
            0 -> fragment = FollowerFragment().apply {
                val mBundle = Bundle()
                mBundle.putString(FollowerFragment.EXTRA_FOLLOWER, username)
                arguments = mBundle
            }
            1 -> fragment = FollowingFragment().apply {
                val mBundle = Bundle()
                mBundle.putString(FollowingFragment.EXTRA_FOLLOWING, username)
                arguments = mBundle
            }

        }
        return fragment as Fragment
    }
}