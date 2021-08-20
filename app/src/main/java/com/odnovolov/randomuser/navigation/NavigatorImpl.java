package com.odnovolov.randomuser.navigation;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.odnovolov.randomuser.MainActivity;
import com.odnovolov.randomuser.R;
import com.odnovolov.randomuser.model.User;
import com.odnovolov.randomuser.model.UserImpl;
import com.odnovolov.randomuser.screen.home.HomeFragment;
import com.odnovolov.randomuser.screen.userprofile.UserProfileFragment;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NavigatorImpl implements Navigator, ActivityLifecycleCallbacks {
    @Inject
    public NavigatorImpl() {
    }

    private FragmentManager fragmentManager;

    @Override
    public void onActivityCreated(
            @NonNull Activity activity,
            @Nullable Bundle savedInstanceState
    ) {
        if (activity instanceof MainActivity) {
            if (savedInstanceState == null) {
                ((MainActivity) activity).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new HomeFragment())
                        .commit();
            }
        }
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        if (activity instanceof MainActivity) {
            fragmentManager = ((MainActivity) activity).getSupportFragmentManager();
        }
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        if (activity instanceof MainActivity) {
            fragmentManager = null;
        }
    }

    @Override
    public void navigateToUserProfile(User user) {
        if (fragmentManager == null) return;
        UserProfileFragment fragment = UserProfileFragment.create((UserImpl) user);
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {}

    @Override
    public void onActivityStopped(@NonNull Activity activity) {}

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {}

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {}
}