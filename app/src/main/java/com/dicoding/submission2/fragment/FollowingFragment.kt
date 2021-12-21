package com.dicoding.submission2.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission2.adapter.UserAdapter
import com.dicoding.submission2.UserResponseItem
import com.dicoding.submission2.viewmodel.FragmentViewModel
import com.dicoding.submission2.databinding.FragmentFollowingBinding


class FollowingFragment : Fragment() {
    private lateinit var binding : FragmentFollowingBinding
    private val fragmentViewModel by viewModels<FragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(this.context)
        binding.rvFollowing.layoutManager = layoutManager

        if(arguments != null){
            val username = arguments?.getString(EXTRA_FOLLOWING)
            fragmentViewModel.showFollowing(username as String)
        }

        fragmentViewModel.userResponseItem.observe(viewLifecycleOwner, { user ->
            showListUser(user)
        })

        fragmentViewModel.isLoading.observe(viewLifecycleOwner,{
            showLoading(it)
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading){
            binding.spinKit.visibility = View.VISIBLE
        }
        else{
            binding.spinKit.visibility = View.GONE
        }
    }

    private fun showListUser(user : List<UserResponseItem>) {
        val adapter= UserAdapter()
        binding.rvFollowing.adapter= adapter
        if(user!=null){
            adapter.setListFav(user)
        }
    }

    companion object {
        const val EXTRA_FOLLOWING = "extra_following"
    }
}