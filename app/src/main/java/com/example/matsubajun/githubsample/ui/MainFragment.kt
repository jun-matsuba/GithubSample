package com.example.matsubajun.githubsample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.navigation.fragment.navArgs
import com.example.matsubajun.githubsample.R
import com.example.matsubajun.githubsample.databinding.FragmentMainBinding
import com.example.matsubajun.githubsample.ui.common.BaseFragment
import com.example.matsubajun.githubsample.ui.follow.FollowListFragment

class MainFragment : BaseFragment() {

    lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        setupViewPager()
        return binding.root
    }

    private fun setupViewPager() {
        val args by navArgs<MainFragmentArgs>()
        val fragmentManager = childFragmentManager
        val pagerAdapter = FollowPagerAdapter(fragmentManager)
        binding.mainViewpager.adapter = pagerAdapter
        binding.mainTab.setupWithViewPager(binding.mainViewpager)
        val position = if (args.extraIsFollow) 0 else 1
        binding.mainViewpager.currentItem = position
    }


    class FollowPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        companion object {
            const val EXTRA_IS_FOLLOW = "extra_is_follow"
        }

        override fun getItem(position: Int): Fragment {
            val fragment = FollowListFragment()
            val bundle = Bundle()
            bundle.putBoolean(EXTRA_IS_FOLLOW, position == 0)

            fragment.arguments = bundle

            return fragment
        }

        override fun getCount() = 2

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> "Followings"
                1 -> "Followers"
                else -> throw IllegalArgumentException()
            }
        }

        //        override fun getItemCount() = 2
    }
}