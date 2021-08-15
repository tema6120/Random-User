package com.odnovolov.randomuser

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

open class BackPressActivity : AppCompatActivity() {
    private val backPressInterceptors: MutableList<() -> Boolean> = ArrayList()

    fun registerOnBackPressed(onBackPressed: () -> Boolean) {
        backPressInterceptors.add(onBackPressed)
    }

    fun unregisterOnBackPressed(onBackPressed: () -> Boolean) {
        backPressInterceptors.remove(onBackPressed)
    }

    override fun onBackPressed() {
        backPressInterceptors.forEach { onBackPressed ->
            val intercepted = onBackPressed()
            if (intercepted) return
        }
        super.onBackPressed()
    }
}

fun Fragment.setOnBackPressed(action: () -> Boolean) {
    lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onResume(owner: LifecycleOwner) {
            super.onResume(owner)
            (requireActivity() as BackPressActivity).registerOnBackPressed(action)
        }

        override fun onPause(owner: LifecycleOwner) {
            super.onPause(owner)
            (requireActivity() as BackPressActivity).unregisterOnBackPressed(action)
        }
    })
}