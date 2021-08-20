package com.odnovolov.randomuser.screen.userlist;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.odnovolov.randomuser.R;
import com.odnovolov.randomuser.databinding.ItemUserBinding;
import com.odnovolov.randomuser.model.User;
import com.odnovolov.randomuser.utils.Utils;

public class UserAdapter extends ListAdapter<User, UserAdapter.UserViewHolder> {
    private final OnClickOnUserListener onClickOnUserListener;

    public UserAdapter(OnClickOnUserListener onClickOnUserListener) {
        super(new DiffCallBack());
        this.onClickOnUserListener = onClickOnUserListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemUserBinding binding = ItemUserBinding.inflate(inflater, parent, false);
        return new UserViewHolder(binding, onClickOnUserListener);
    }

    @Override
    public void onBindViewHolder(
            @NonNull UserViewHolder viewHolder,
            int position
    ) {
        User user = getItem(position);
        viewHolder.bind(user);
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        private final ItemUserBinding binding;
        private final OnClickOnUserListener onClickOnUserListener;

        public UserViewHolder(
                ItemUserBinding binding,
                OnClickOnUserListener onClickOnUserListener
        ) {
            super(binding.getRoot());
            this.binding = binding;
            this.onClickOnUserListener = onClickOnUserListener;
        }

        void bind(User user) {
            Glide.with(binding.getRoot().getContext())
                    .load(user.getPhotoUrl())
                    .transform(new RoundedCorners(Utils.dpToPx(24)))
                    .into(binding.userPhotoImageView);

            String userName = user.getFirstName() + " " + user.getLastName();
            binding.userNameTextView.setText(userName);

            String age = binding.ageTextView.getResources().getQuantityString(
                    R.plurals.years,
                    user.getAge(),
                    user.getAge()
            );
            binding.ageTextView.setText(age);

            binding.getRoot().setOnClickListener(
                    v -> onClickOnUserListener.onClickOnUser(user)
            );
        }
    }

    public static class DiffCallBack extends DiffUtil.ItemCallback<User> {
        @Override
        public boolean areItemsTheSame(
                @NonNull User oldItem,
                @NonNull User newItem
        ) {
            return oldItem.getId().equals(newItem.getId());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(
                @NonNull User oldItem,
                @NonNull User newItem
        ) {
            return oldItem.equals(newItem);
        }
    }

    public interface OnClickOnUserListener {
        void onClickOnUser(User user);
    }
}