package com.odnovolov.randomuser;

import android.app.Application;

import com.odnovolov.randomuser.navigation.NavigatorImpl;

import javax.inject.Inject;

public class App extends Application {
    private AppComponent appComponent;

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createComponent();
        appComponent.inject(this);
    }

    private void createComponent() {
        appComponent = DaggerAppComponent.builder()
                .context(this)
                .build();
    }

    @Inject
    void registerNavigatorImpl(NavigatorImpl navigatorImpl) {
        registerActivityLifecycleCallbacks(navigatorImpl);
    }
}