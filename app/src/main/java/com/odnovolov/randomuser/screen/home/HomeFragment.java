package com.odnovolov.randomuser.screen.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.odnovolov.randomuser.utils.BackPressActivity;
import com.odnovolov.randomuser.databinding.FragmentHomeBinding;
import com.odnovolov.randomuser.utils.FragmentAwareOfFirstViewCreation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class HomeFragment extends FragmentAwareOfFirstViewCreation {
    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(
            @NotNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);
        binding.homeViewPager.setOffscreenPageLimit(3);
        binding.homeViewPager.setAdapter(new HomePagerAdapter(this));
        binding.homeViewPager.registerOnPageChangeCallback(onPageChangeCallback);

        binding.menButton.setOnClickListener(v -> binding.homeViewPager.setCurrentItem(0));
        binding.randomPersonButton.setOnClickListener(v -> binding.homeViewPager.setCurrentItem(1));
        binding.womenButton.setOnClickListener(v -> binding.homeViewPager.setCurrentItem(2));

        if (isViewFirstCreated()) {
            binding.homeViewPager.setCurrentItem(1, false);
        }
    }

    private final ViewPager2.OnPageChangeCallback onPageChangeCallback =
            new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    updateBottomNavigationBar();
                }
            };

    private void updateBottomNavigationBar() {
        int selectedPosition = binding.homeViewPager.getCurrentItem();

        binding.menButton.setSelected(selectedPosition == 0);
        binding.randomPersonButton.setSelected(selectedPosition == 1);
        binding.womenButton.setSelected(selectedPosition == 2);

        binding.menTextView.setVisibility(selectedPosition == 0 ? VISIBLE : GONE);
        binding.randomPersonTextView.setVisibility(selectedPosition == 1 ? VISIBLE : GONE);
        binding.womenTextView.setVisibility(selectedPosition == 2 ? VISIBLE : GONE);

        if (!binding.bottomNavigationBar.isLaidOut()) {
            binding.bottomNavigationBar.post(
                    () -> binding.bottomNavigationBar.requestLayout()
            );
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        BackPressActivity activity = (BackPressActivity) requireActivity();
        activity.registerOnBackPressInterceptor(onBackPressInterceptor);
    }

    @Override
    public void onPause() {
        super.onPause();
        BackPressActivity activity = (BackPressActivity) requireActivity();
        activity.unregisterOnBackPressInterceptor(onBackPressInterceptor);
    }

    private final BackPressActivity.OnBackPressInterceptor onBackPressInterceptor = () -> {
        if (binding.homeViewPager.getCurrentItem() != 1) {
            binding.homeViewPager.setCurrentItem(1);
            return true;
        } else {
            return false;
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.homeViewPager.setAdapter(null);
        binding.homeViewPager.unregisterOnPageChangeCallback(onPageChangeCallback);
        binding = null;
    }
}