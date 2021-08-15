package com.odnovolov.randomuser.screen.userprofile

import com.odnovolov.randomuser.model.User
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.StateStrategyType

interface UserProfileView : MvpView {
    @StateStrategyType(value = AddToEndSingleTagStrategy::class, tag = "status")
    fun showUserProfile(user: User)

    @StateStrategyType(value = AddToEndSingleTagStrategy::class, tag = "status")
    fun showProgress()

    @StateStrategyType(value = AddToEndSingleTagStrategy::class, tag = "status")
    fun showError()
}