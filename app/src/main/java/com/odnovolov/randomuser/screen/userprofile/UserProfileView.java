package com.odnovolov.randomuser.screen.userprofile;

import com.odnovolov.randomuser.model.User;
import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleTagStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface UserProfileView extends MvpView {
    @StateStrategyType(value = AddToEndSingleTagStrategy.class, tag = "status")
    void showUserProfile(User user);

    @StateStrategyType(value = AddToEndSingleTagStrategy.class, tag = "status")
    void showProgress();

    @StateStrategyType(value = AddToEndSingleTagStrategy.class, tag = "status")
    void showError();
}