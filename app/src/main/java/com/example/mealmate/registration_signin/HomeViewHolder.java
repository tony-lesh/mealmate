package com.example.mealmate.registration_signin;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealmate.R;
import com.example.mealmate.recipe.RecipeBean;

public class HomeViewHolder extends RecyclerView.ViewHolder{

    TextView homeRecipeName,  homeRecipeDescription ;
    ImageView homeRecipeImage, likeBtn;

    public HomeViewHolder(@NonNull View itemView) {
        super(itemView);
        homeRecipeImage = itemView.findViewById(R.id.mealImageView);
        homeRecipeName = itemView.findViewById(R.id.mealName);
        homeRecipeDescription = itemView.findViewById(R.id.mealDescription);
        likeBtn = itemView.findViewById(R.id.likeBtn);
    }

    public void bind(HomeBean homeBean){
// Load image using Glide or Picasso
        Glide.with(itemView.getContext())
                .load(homeBean.getImageURL())
                .into(homeRecipeImage);
        homeRecipeName.setText(homeBean.getTitle());
        homeRecipeDescription.setText(homeBean.getDescription());
    }
}
