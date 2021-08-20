package com.odnovolov.randomuser.screen.userlist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.odnovolov.randomuser.App;
import com.odnovolov.randomuser.databinding.FragmentUserListBinding;
import com.odnovolov.randomuser.model.User;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class UserListFragment extends MvpAppCompatFragment implements UserListView {
    public static String ARG_LIST_FILTER = "ARG_LIST_FILTER";

    @NotNull
    public static UserListFragment create(UserListFilter listFilter) {
        UserListFragment fragment = new UserListFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_LIST_FILTER, listFilter);
        fragment.setArguments(arguments);
        return fragment;
    }

    private UserAdapter adapter;

    private FragmentUserListBinding binding;

    @Inject
    UserListPresenter.Factory presenterFactory;

    @InjectPresenter
    UserListPresenter presenter;

    @ProvidePresenter
    UserListPresenter provideUserListPresenter() {
        UserListFilter userListFilter = null;
        try {
            userListFilter = (UserListFilter) getArguments().getSerializable(ARG_LIST_FILTER);
        } catch (Exception e) {
            // arguments don't contain userListFilter
        }
        return presenterFactory.create(userListFilter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        App app = (App) requireContext().getApplicationContext();
        app.getAppComponent().inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new UserAdapter(user -> presenter.onClickOnUser(user));
    }

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentUserListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(
            @NotNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);
        binding.userListRecyclerView.setAdapter(adapter);
        binding.errorLayout.retryButton.setOnClickListener(
                v -> presenter.onRetryButtonClicked()
        );
    }

    @Override
    public void showUsers(@NotNull List<User> users) {
        adapter.submitList(users);
        show(binding.userListRecyclerView);
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
        updateVisibility(binding.userListRecyclerView, theOnlyVisibleView);
        updateVisibility(binding.progressBar, theOnlyVisibleView);
        updateVisibility(binding.errorLayout.getRoot(), theOnlyVisibleView);
    }

    private void updateVisibility(View targetView, View theOnlyVisibleView) {
        targetView.setVisibility(targetView == theOnlyVisibleView ? VISIBLE : GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.userListRecyclerView.setAdapter(null);
        binding = null;
    }
}