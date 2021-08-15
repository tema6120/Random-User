package com.odnovolov.randomuser.screen.userprofile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.odnovolov.randomuser.R
import com.odnovolov.randomuser.model.User
import com.odnovolov.randomuser.utils.dp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.error.*
import kotlinx.android.synthetic.main.fragment_user_profile.*
import kotlinx.android.synthetic.main.fragment_user_profile.userProfileContent
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

@AndroidEntryPoint
class UserProfileFragment : MvpAppCompatFragment(), UserProfileView {
    companion object {
       const val ARG_USER = "ARG_USER"

       fun create(user: User) = UserProfileFragment().apply {
           arguments = bundleOf(ARG_USER to user)
       }
    }

    @Inject lateinit var presenterFactory: UserProfilePresenter.Factory

    private val presenter: UserProfilePresenter by moxyPresenter {
        val userFromArguments: User? = arguments?.get(ARG_USER) as? User
        presenterFactory.create(userFromArguments)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retryButton.setOnClickListener {
            presenter.onRetryButtonClicked()
        }
    }

    override fun showUserProfile(user: User) {
        Glide.with(this)
            .load(user.photoUrl)
            .transform(RoundedCorners(75.dp))
            .into(userPhotoImageView)

        @SuppressLint("SetTextI18n")
        userNameTextView.text = "${user.firstName} ${user.lastName}"
        ageTextView.text = user.age.toString()
        cityTextView.text = user.city
        emailTextView.text = user.email

        show(userProfileContent)
    }

    override fun showProgress() {
        show(progressBar)
    }

    override fun showError() {
        show(errorLayout)
    }

    private fun show(theOnlyVisibleView: View) {
        userProfileContent.isVisible = userProfileContent == theOnlyVisibleView
        progressBar.isVisible = progressBar == theOnlyVisibleView
        errorLayout.isVisible = errorLayout == theOnlyVisibleView
    }
}