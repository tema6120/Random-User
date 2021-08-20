package com.odnovolov.randomuser.screen.home;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.odnovolov.randomuser.screen.userlist.UserListFilter;
import com.odnovolov.randomuser.screen.userlist.UserListFragment;
import com.odnovolov.randomuser.screen.userprofile.UserProfileFragment;

import org.jetbrains.annotations.NotNull;

public class HomePagerAdapter extends FragmentStateAdapter {
    public HomePagerAdapter(Fragment fragment) {
        super(fragment);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @NotNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return UserListFragment.create(UserListFilter.ONLY_MEN);
            case 1:
                return new UserProfileFragment();
            case 2:
                return UserListFragment.create(UserListFilter.ONLY_WOMEN);
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
    }
}