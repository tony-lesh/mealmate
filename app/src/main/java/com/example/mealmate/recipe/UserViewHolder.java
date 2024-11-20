package com.example.mealmate.recipe;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealmate.R;

public class UserViewHolder extends RecyclerView.ViewHolder {

    TextView recipeName,  recipeIngredients ;
    ImageView recipeImage;
    ImageView recipeAddToCart, recipeLike;
    ImageView recipeRate;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
       recipeImage = itemView.findViewById(R.id.recipeImage);
       recipeName = itemView.findViewById(R.id.recipeName);
       recipeIngredients = itemView.findViewById(R.id.recipeIngredients);
       recipeAddToCart = itemView.findViewById(R.id.recipeAddToCart);
       recipeRate = itemView.findViewById(R.id.recipeRate);
       recipeLike = itemView.findViewById(R.id.recipeLike);
    }

    public void bind(RecipeBean recipeBean){
// Load image using Glide or Picasso
        Glide.with(itemView.getContext())
                .load(recipeBean.getImageURL())
                .into(recipeImage);
        recipeName.setText(recipeBean.getTitle());
        recipeIngredients.setText(recipeBean.getIngredients());
    }
}
