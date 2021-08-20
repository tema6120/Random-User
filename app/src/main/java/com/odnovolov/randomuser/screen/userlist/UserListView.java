package com.odnovolov.randomuser.screen.userlist;

import com.odnovolov.randomuser.model.User;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleTagStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface UserListView extends MvpView {
    @StateStrategyType(value = AddToEndSingleTagStrategy.class, tag = "status")
    void showUsers(List<User> users);

    @StateStrategyType(value = AddToEndSingleTagStrategy.class, tag = "status")
    void showProgress();

    @StateStrategyType(value = AddToEndSingleTagStrategy.class, tag = "status")
    void showError();
}