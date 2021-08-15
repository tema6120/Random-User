package com.odnovolov.randomuser.screen.userlist

import com.odnovolov.randomuser.model.User
import com.odnovolov.randomuser.navigation.Navigator
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

class UserListPresenter @AssistedInject constructor(
    private val navigator: Navigator,
    private val randomUserService: RandomUserService,
    @Assisted private val userListFilter: UserListFilter?
) : MvpPresenter<UserListView>() {
    @AssistedFactory
    interface Factory {
        fun create(userListFilter: UserListFilter?): UserListPresenter
    }

    override fun onFirstViewAttach() {
        loadUsers()
    }

    fun onClickOnUser(user: User) {
        navigator.navigateToUserProfile(user)
    }

    fun onRetryButtonClicked() {
        loadUsers()
    }

    private fun loadUsers() {
        viewState.showProgress()
        val gender = when (userListFilter) {
            UserListFilter.ONLY_MEN -> "male"
            UserListFilter.ONLY_WOMEN -> "female"
            else -> null
        }
        presenterScope.launch {
            val response: Response<RandomUserResponse> =
                try {
                    randomUserService.getRandomUsers(number = 20, gender)
                } catch (e: Exception) {
                    // let the user see the progress bar
                    delay(500)
                    viewState.showError()
                    return@launch
                }
            if (response.isSuccessful) {
                val users: List<User>? = response.body()?.toUsers()
                if (users != null) {
                    viewState.showUsers(users)
                } else {
                    viewState.showError()
                }
            } else {
                viewState.showError()
            }
        }
    }
}