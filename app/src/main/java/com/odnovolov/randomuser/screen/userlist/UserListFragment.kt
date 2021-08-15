package com.odnovolov.randomuser.screen.userlist

import android.os.Bundle
import android.transition.Fade
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import com.odnovolov.randomuser.R
import com.odnovolov.randomuser.model.User
import com.odnovolov.randomuser.screen.userprofile.UserProfileFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.error.*
import kotlinx.android.synthetic.main.fragment_user_list.*
import kotlinx.android.synthetic.main.item_user.view.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

@AndroidEntryPoint
class UserListFragment : MvpAppCompatFragment(), UserListView {
    companion object {
        const val ARG_LIST_FILTER = "ARG_LIST_FILTER"

        fun create(listFilter: UserListFilter) = UserListFragment().apply {
            arguments = bundleOf(ARG_LIST_FILTER to listFilter)
        }
    }

    @Inject lateinit var presenterFactory: UserListPresenter.Factory

    private val presenter: UserListPresenter by moxyPresenter {
        val userListFilter: UserListFilter? =
            arguments?.getSerializable(ARG_LIST_FILTER) as? UserListFilter
        presenterFactory.create(userListFilter)
    }

    private val adapter by lazy {
        UserAdapter(presenter::onClickOnUser)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userListRecyclerView.adapter = adapter
        retryButton.setOnClickListener {
            presenter.onRetryButtonClicked()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        userListRecyclerView.adapter = null
    }

    override fun showUsers(users: List<User>) {
        adapter.submitList(users)
        show(userListRecyclerView)
    }

    override fun showProgress() {
        show(progressBar)
    }

    override fun showError() {
        show(errorLayout)
    }

    private fun show(theOnlyVisibleView: View) {
        userListRecyclerView.isVisible = userListRecyclerView == theOnlyVisibleView
        progressBar.isVisible = progressBar == theOnlyVisibleView
        errorLayout.isVisible = errorLayout == theOnlyVisibleView
    }
}