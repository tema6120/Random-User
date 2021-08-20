package com.odnovolov.randomuser;

import android.content.Context;

import com.odnovolov.randomuser.navigation.NavigationModule;
import com.odnovolov.randomuser.network.NetworkModule;
import com.odnovolov.randomuser.screen.userlist.UserListFragment;
import com.odnovolov.randomuser.screen.userprofile.UserProfileFragment;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = {
        NavigationModule.class,
        NetworkModule.class
})
@Singleton
public interface AppComponent {
    void inject(App app);
    void inject(UserListFragment fragment);
    void inject(UserProfileFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder context(Context context);
        AppComponent build();
    }
}