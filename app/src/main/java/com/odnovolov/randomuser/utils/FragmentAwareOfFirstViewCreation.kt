package com.odnovolov.randomuser.utils

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

open class FragmentAwareOfFirstViewCreation : Fragment() {
    private var hasSavedInstanceState = false
    private var numberOfOnViewCreatedInvocation = 0

    protected val isViewFirstCreated: Boolean
        get() = !hasSavedInstanceState && numberOfOnViewCreatedInvocation <= 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        hasSavedInstanceState = savedInstanceState != null
        numberOfOnViewCreatedInvocation++
        super.onViewCreated(view, savedInstanceState)
    }
}