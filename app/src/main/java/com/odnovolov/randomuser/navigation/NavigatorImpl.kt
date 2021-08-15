package com.odnovolov.randomuser.navigation

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.odnovolov.randomuser.MainActivity
import com.odnovolov.randomuser.R
import com.odnovolov.randomuser.model.User
import com.odnovolov.randomuser.screen.home.HomeFragment
import com.odnovolov.randomuser.screen.userprofile.UserProfileFragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigatorImpl @Inject constructor() : Navigator, ActivityLifecycleCallbacks {
    private var fragmentManager: FragmentManager? = null

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activity is MainActivity) {
            if (savedInstanceState == null) {
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, HomeFragment())
                    .commit()
            }
        }
    }

    override fun onActivityResumed(activity: Activity) {
        if (activity is MainActivity) {
            fragmentManager = activity.supportFragmentManager
        }
    }

    override fun onActivityPaused(activity: Activity) {
        if (activity is MainActivity) {
            fragmentManager = null
        }
    }

    override fun navigateToUserProfile(user: User) {
        val fragmentManager: FragmentManager = this.fragmentManager ?: return
        val fragment: UserProfileFragment = UserProfileFragment.create(user)
        fragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}
}