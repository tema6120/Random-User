package com.odnovolov.randomuser.navigation;

import dagger.Binds;
import dagger.Module;

@Module
public interface NavigationModule {
    @Binds
    Navigator bindNavigator(NavigatorImpl navigatorImpl);
}