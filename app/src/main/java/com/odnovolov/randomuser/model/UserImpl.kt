package com.odnovolov.randomuser.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserImpl(
    override val id: String,
    override val firstName: String,
    override val lastName: String,
    override val photoUrl: String,
    override val age: Int,
    override val city: String,
    override val email: String
) : User, Parcelable