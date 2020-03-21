package com.hsun.myapplication.ui;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hsun.myapplication.R;
import com.hsun.myapplication.databinding.UserItemBinding;
import com.hsun.myapplication.model.User;
import com.hsun.myapplication.utils.DeepCopy;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList = new ArrayList<>();

    @SuppressWarnings("unchecked")
    void setUserList(@NonNull final List<User> users) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return userList.size();
            }

            @Override
            public int getNewListSize() {
                return users.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                User old = userList.get(oldItemPosition);
                User user = users.get(newItemPosition);
                return TextUtils.equals(old.getName(), user.getName());
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                User old = userList.get(oldItemPosition);
                User user = users.get(newItemPosition);
                return TextUtils.equals(old.getName(), user.getName()) &&
                        (old.getTotalUser() == user.getTotalUser());
            }
        });
        userList = (List<User>) DeepCopy.cloneArray(users);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    @NonNull
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.user_item, parent, false);
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.binding.setUser(userList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return userList == null ? 0 : userList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {

        final UserItemBinding binding;

        UserViewHolder(UserItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
