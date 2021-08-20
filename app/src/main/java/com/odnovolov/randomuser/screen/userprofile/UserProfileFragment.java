package com.odnovolov.randomuser.screen.userprofile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.odnovolov.randomuser.App;
import com.odnovolov.randomuser.databinding.FragmentUserProfileBinding;
import com.odnovolov.randomuser.model.User;
import com.odnovolov.randomuser.model.UserImpl;
import com.odnovolov.randomuser.utils.Utils;

import javax.inject.Inject;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class UserProfileFragment extends MvpAppCompatFragment implements UserProfileView {
    public static String ARG_USER = "ARG_USER";

    @NonNull
    public static UserProfileFragment create(UserImpl user) {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARG_USER, user);
        fragment.setArguments(arguments);
        return fragment;
    }

    private FragmentUserProfileBinding binding;

    @Inject
    UserProfilePresenter.Factory presenterFactory;

    @InjectPresenter
    UserProfilePresenter presenter;

    @ProvidePresenter
    UserProfilePresenter provideUserProfilePresenter() {
        User user = null;
        try {
            user = getArguments().getParcelable(ARG_USER);
        } catch (Exception e) {
            // arguments don't contain user
        }
        return presenterFactory.create(user);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        App app = (App) requireContext().getApplicationContext();
        app.getAppComponent().inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);
        binding.errorLayout.retryButton.setOnClickListener(
                v -> presenter.onRetryButtonClicked()
        );
    }

    @Override
    public void showUserProfile(User user) {
        Glide.with(this)
                .load(user.getPhotoUrl())
                .transform(new RoundedCorners(Utils.dpToPx(75)))
                .into(binding.userPhotoImageView);

        String userName = user.getFirstName() + " " + user.getLastName();
        binding.userNameTextView.setText(userName);

        String age = String.valueOf(user.getAge());
        binding.ageTextView.setText(age);

        binding.cityTextView.setText(user.getCity());

        binding.emailTextView.setText(user.getEmail());

        show(binding.userProfileContent);
    }

    @Override
    public void showProgress() {
        show(binding.progressBar);
    }

    @Override
    public void showError() {
        show(binding.errorLayout.getRoot());
    }

    private void show(View theOnlyVisibleView) {
        updateVisibility(binding.userProfileContent, theOnlyVisibleView);
        updateVisibility(binding.progressBar, theOnlyVisibleView);
        updateVisibility(binding.errorLayout.getRoot(), theOnlyVisibleView);
    }

    private void updateVisibility(View targetView, View theOnlyVisibleView) {
        targetView.setVisibility(targetView == theOnlyVisibleView ? VISIBLE : GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}