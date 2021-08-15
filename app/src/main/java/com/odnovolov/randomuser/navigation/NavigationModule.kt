package com.odnovolov.randomuser.navigation

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {
    @Binds
    fun bindNavigator(navigatorImpl: NavigatorImpl) : Navigator
}