package com.odnovolov.randomuser.navigation

import com.odnovolov.randomuser.model.User

interface Navigator {
    fun navigateToUserProfile(user: User)
}