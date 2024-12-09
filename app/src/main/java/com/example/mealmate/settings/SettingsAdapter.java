package com.example.mealmate.settings;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealmate.R;
import com.example.mealmate.settings.user_profile.ProfileActivity;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder>{

    @NonNull
    @Override
    public SettingsAdapter.SettingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_profile, parent, false);
        return new SettingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsAdapter.SettingsViewHolder holder, int position) {

        holder.bind(new SettingsBean());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "You Clicked Profile!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(v.getContext(), ProfileActivity.class);
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class SettingsViewHolder extends RecyclerView.ViewHolder {

        TextView txtNames, txtEmail;
        ImageView profileImage;

        public SettingsViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNames = itemView.findViewById(R.id.txtNames);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            profileImage = itemView.findViewById(R.id.profileImage);
        }

        public void bind (SettingsBean settingsBean){

            // Load image using Glide or Picasso
            Glide.with(itemView.getContext())
                    .load(settingsBean.getImageUrl())
                    .into(profileImage);
            txtNames.setText(settingsBean.getName());
            txtEmail.setText(settingsBean.getEmail());
        }
    }
}
