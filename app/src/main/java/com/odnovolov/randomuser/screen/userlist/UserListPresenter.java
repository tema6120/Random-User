package com.odnovolov.randomuser.screen.userlist;

import android.os.Handler;
import android.os.Looper;

import com.odnovolov.randomuser.model.User;
import com.odnovolov.randomuser.navigation.Navigator;
import com.odnovolov.randomuser.network.RandomUserService;
import com.odnovolov.randomuser.network.model.Mapper;
import com.odnovolov.randomuser.network.model.RandomUserResponse;

import java.util.List;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.MvpPresenter;
import retrofit2.Response;

public class UserListPresenter extends MvpPresenter<UserListView> {
    @AssistedFactory
    public interface Factory {
        UserListPresenter create(UserListFilter userListFilter);
    }

    private final Navigator navigator;
    private final RandomUserService randomUserService;
    private final UserListFilter userListFilter;
    private Disposable disposable;

    @AssistedInject
    public UserListPresenter(
            Navigator navigator,
            RandomUserService randomUserService,
            @Assisted UserListFilter userListFilter
    ) {
        this.navigator = navigator;
        this.randomUserService = randomUserService;
        this.userListFilter = userListFilter;
    }

    @Override
    protected void onFirstViewAttach() {
        loadUsers();
    }

    public void onClickOnUser(User user) {
        navigator.navigateToUserProfile(user);
    }

    public void onRetryButtonClicked() {
        loadUsers();
    }

    private void loadUsers() {
        getViewState().showProgress();
        if (disposable != null) {
            disposable.dispose();
        }
        String gender = null;
        switch (userListFilter) {
            case ONLY_MEN:
                gender = "male";
                break;
            case ONLY_WOMEN:
                gender = "female";
                break;
        }
        disposable = randomUserService.getRandomUsers(20, gender)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::handleResponse,
                        error -> scheduleShowingError()
                );
    }

    private void handleResponse(Response<RandomUserResponse> response) {
        if (response.isSuccessful()) {
            RandomUserResponse randomUserResponse = response.body();
            if (randomUserResponse == null) {
                scheduleShowingError();
                return;
            }
            List<User> users = Mapper.userResponseToUsers(randomUserResponse);
            getViewState().showUsers(users);
        } else {
            scheduleShowingError();
        }
    }

    private void scheduleShowingError() {
        // let the user see the progress bar
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> getViewState().showError(), 500);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}