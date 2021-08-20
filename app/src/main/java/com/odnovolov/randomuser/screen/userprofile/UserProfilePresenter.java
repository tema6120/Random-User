package com.odnovolov.randomuser.screen.userprofile;

import android.os.Handler;
import android.os.Looper;

import com.odnovolov.randomuser.model.User;
import com.odnovolov.randomuser.network.RandomUserService;
import com.odnovolov.randomuser.network.model.Mapper;
import com.odnovolov.randomuser.network.model.RandomUserResponse;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.MvpPresenter;
import retrofit2.Response;

public class UserProfilePresenter extends MvpPresenter<UserProfileView> {
    @AssistedFactory
    interface Factory {
        UserProfilePresenter create(User user);
    }

    private final RandomUserService randomUserService;
    private User user;
    private Disposable disposable;

    @AssistedInject
    public UserProfilePresenter(
            RandomUserService randomUserService,
            @Assisted User user
    ) {
        this.randomUserService = randomUserService;
        this.user = user;
    }

    @Override
    public void onFirstViewAttach() {
        if (user == null) {
            loadUser();
        } else {
            getViewState().showUserProfile(user);
        }
    }

    void onRetryButtonClicked() {
        loadUser();
    }

    private void loadUser() {
        getViewState().showProgress();
        if (disposable != null) {
            disposable.dispose();
        }
        disposable = randomUserService.getRandomUsers(1, null)
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
            user = Mapper.userResponseToUsers(randomUserResponse).get(0);
            if (user != null) {
                getViewState().showUserProfile(user);
            } else {
                scheduleShowingError();
            }
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