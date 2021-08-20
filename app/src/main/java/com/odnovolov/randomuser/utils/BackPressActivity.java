package com.odnovolov.randomuser.utils;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BackPressActivity extends AppCompatActivity {
    public interface OnBackPressInterceptor {
        boolean onBackPressed();
    }

    private final ArrayList<OnBackPressInterceptor> backPressInterceptors = new ArrayList<>();

    public void registerOnBackPressInterceptor(OnBackPressInterceptor onBackPressInterceptor) {
        backPressInterceptors.add(onBackPressInterceptor);
    }

    public void unregisterOnBackPressInterceptor(OnBackPressInterceptor onBackPressInterceptor) {
        backPressInterceptors.remove(onBackPressInterceptor);
    }

    @Override
    public void onBackPressed() {
        for (OnBackPressInterceptor onBackPressInterceptor : backPressInterceptors) {
            boolean intercepted = onBackPressInterceptor.onBackPressed();
            if (intercepted) return;
        }
        super.onBackPressed();
    }
}