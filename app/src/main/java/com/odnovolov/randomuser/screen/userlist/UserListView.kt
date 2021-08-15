package com.odnovolov.randomuser.screen.userlist

import com.odnovolov.randomuser.model.User
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.OneExecution

interface UserListView : MvpView {
    @StateStrategyType(value = AddToEndSingleTagStrategy::class, tag = "status")
    fun showUsers(users: List<User>)

    @StateStrategyType(value = AddToEndSingleTagStrategy::class, tag = "status")
    fun showProgress()

    @StateStrategyType(value = AddToEndSingleTagStrategy::class, tag = "status")
    fun showError()
}