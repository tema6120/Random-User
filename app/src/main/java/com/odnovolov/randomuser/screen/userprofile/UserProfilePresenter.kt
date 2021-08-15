package com.odnovolov.randomuser.screen.userprofile

import com.odnovolov.randomuser.model.User
import com.odnovolov.randomuser.network.RandomUserService
import com.odnovolov.randomuser.network.model.RandomUserResponse
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import retrofit2.Response

class UserProfilePresenter @AssistedInject constructor(
    private val randomUserService: RandomUserService,
    @Assisted private var user: User? // null means random user
): MvpPresenter<UserProfileView>() {
    @AssistedFactory
    interface Factory {
        fun create(user: User?): UserProfilePresenter
    }

    override fun onFirstViewAttach() {
        user?.let(viewState::showUserProfile) ?: loadUser()
    }

    fun onRetryButtonClicked() {
        loadUser()
    }

    private fun loadUser() {
        viewState.showProgress()
        presenterScope.launch {
            val response: Response<RandomUserResponse> =
                try {
                    randomUserService.getRandomUsers(number = 1)
                } catch (e: Exception) {
                    // let the user see the progress bar
                    delay(500)
                    viewState.showError()
                    return@launch
                }
            if (response.isSuccessful) {
                val user: User? = response.body()?.toUsers()?.firstOrNull()
                if (user != null) {
                    this@UserProfilePresenter.user = user
                    viewState.showUserProfile(user)
                } else {
                    viewState.showError()
                }
            } else {
                viewState.showError()
            }
        }
    }
}