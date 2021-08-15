package com.odnovolov.randomuser.screen.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.odnovolov.randomuser.R
import com.odnovolov.randomuser.setOnBackPressed
import com.odnovolov.randomuser.utils.FragmentAwareOfFirstViewCreation
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : FragmentAwareOfFirstViewCreation() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOnBackPressed {
            if (homeViewPager.currentItem != 1) {
                homeViewPager.currentItem = 1
                true
            } else {
                false
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewPager.offscreenPageLimit = 3
        homeViewPager.adapter = HomePagerAdapter(fragment = this)
        homeViewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateBottomNavigationBar()
            }
        })

        menButton         .setOnClickListener { homeViewPager.currentItem = 0 }
        randomPersonButton.setOnClickListener { homeViewPager.currentItem = 1 }
        womenButton       .setOnClickListener { homeViewPager.currentItem = 2 }

        if (isViewFirstCreated) {
            homeViewPager.setCurrentItem(1, /*smoothScroll =*/ false)
        }
    }

    private fun updateBottomNavigationBar() {
        val selectedPosition: Int = homeViewPager.currentItem

        menButton         .isSelected = selectedPosition == 0
        randomPersonButton.isSelected = selectedPosition == 1
        womenButton       .isSelected = selectedPosition == 2

        menTextView         .isVisible = selectedPosition == 0
        randomPersonTextView.isVisible = selectedPosition == 1
        womenTextView       .isVisible = selectedPosition == 2

        with(bottomNavigationBar) {
            if (!isLaidOut) {
                post {
                    requestLayout()
                }
            }
        }
    }
}