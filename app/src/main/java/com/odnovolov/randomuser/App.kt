package com.odnovolov.randomuser

import android.app.Application
import com.odnovolov.randomuser.navigation.NavigatorImpl
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    @Inject
    fun registerNavigatorImpl(navigatorImpl: NavigatorImpl) {
        registerActivityLifecycleCallbacks(navigatorImpl)
    }
}