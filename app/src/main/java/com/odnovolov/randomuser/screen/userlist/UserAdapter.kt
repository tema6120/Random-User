package com.odnovolov.randomuser.screen.userlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.odnovolov.randomuser.R
import com.odnovolov.randomuser.model.User
import com.odnovolov.randomuser.utils.SimpleViewHolder
import com.odnovolov.randomuser.utils.dp
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(
    private val onClickOnUser: (user: User) -> Unit
) : ListAdapter<User, SimpleViewHolder>(DiffCallBack()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SimpleViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val itemView: View = inflater.inflate(R.layout.item_user, parent, false)
        return SimpleViewHolder(itemView)
    }

    override fun onBindViewHolder(
        viewHolder: SimpleViewHolder,
        position: Int
    ) {
        val user: User = getItem(position)
        with(viewHolder.itemView) {
            Glide.with(context)
                .load(user.photoUrl)
                .transform(RoundedCorners(24.dp))
                .into(userPhotoImageView)

            @SuppressLint("SetTextI18n")
            userNameTextView.text = "${user.firstName} ${user.lastName}"

            ageTextView.text = resources.getQuantityString(
                R.plurals.years,
                user.age,
                user.age
            )

            setOnClickListener {
                onClickOnUser(user)
            }
        }
    }

    class DiffCallBack : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(
            oldItem: User,
            newItem: User
        ): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: User,
            newItem: User
        ): Boolean {
            return oldItem == newItem
        }
    }
}