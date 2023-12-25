package com.example.taxfileapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<User> users;
    private OnUserClickListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView fullNameTextView;
        public TextView phoneTextView;
        public TextView cityTextView;
        public TextView companyNameTextView;
        public TextView statusTextView;

        public ViewHolder(View view) {
            super(view);
            fullNameTextView = view.findViewById(R.id.fullNameTextView);
            phoneTextView = view.findViewById(R.id.phoneTextView);
            cityTextView = view.findViewById(R.id.cityTextView);
            companyNameTextView = view.findViewById(R.id.companyTextView);
            statusTextView = view.findViewById(R.id.statusTextView);
        }
    }

    public RecyclerAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        holder.fullNameTextView.setText(user.fullName);
        holder.phoneTextView.setText(user.phone);
        holder.cityTextView.setText(user.city);
        holder.companyNameTextView.setText(user.companyName);
        holder.statusTextView.setText(user.processStatus);

        // Set background color dynamically based on processStatus
        int bgColor;
        switch (user.processStatus) {
            case "AWAITED":
                bgColor = R.color.awaited_color;
                break;
            case "COMPLETED":
                bgColor = R.color.completed_color;
                break;
            case "DENIED":
                bgColor = R.color.denied_color;
                break;
            default:
                bgColor = android.R.color.transparent;
                break;
        }
        holder.itemView.setBackgroundResource(bgColor);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onUserClick(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public interface OnUserClickListener {
        void onUserClick(User user);
    }

    public void setOnUserClickListener(OnUserClickListener listener) {
        this.listener = listener;
    }

    public void setData(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    public User getUserAtPosition(int position) {
        return users.get(position);
    }

    public void removeUserAtPosition(int position) {
        users.remove(position);
        notifyItemRemoved(position);
    }
}
