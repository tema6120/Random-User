package com.odnovolov.randomuser.screen.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.odnovolov.randomuser.screen.userlist.UserListFilter
import com.odnovolov.randomuser.screen.userlist.UserListFragment
import com.odnovolov.randomuser.screen.userprofile.UserProfileFragment

class HomePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UserListFragment.create(UserListFilter.ONLY_MEN)
            1 -> UserProfileFragment()
            else -> UserListFragment.create(UserListFilter.ONLY_WOMEN)
        }
    }
}