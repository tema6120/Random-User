package com.odnovolov.randomuser.utils;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentAwareOfFirstViewCreation extends Fragment {
    private boolean hasSavedInstanceState = false;
    private int numberOfOnViewCreatedInvocation = 0;

    public boolean isViewFirstCreated() {
        return !hasSavedInstanceState && numberOfOnViewCreatedInvocation <= 1;
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        hasSavedInstanceState = savedInstanceState != null;
        numberOfOnViewCreatedInvocation++;
        super.onViewCreated(view, savedInstanceState);
    }
}