package com.example.mealmate.mealPlan.breakfast;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealmate.R;
import com.example.mealmate.mealPlan.Meal;

public class BreakfastViewHolder extends RecyclerView.ViewHolder {

    TextView recipeName;
    ImageView recipeImage;

    public BreakfastViewHolder(@NonNull View itemView) {
        super(itemView);
        recipeImage = itemView.findViewById(R.id.breakfastImageView);
        recipeName = itemView.findViewById(R.id.breakfastName);
    }

    public void bind(Meal meal) {
        // Load image using Glide
        Glide.with(itemView.getContext())
                .load(meal.getImageUrl())
                .into(recipeImage);

        // Set recipe title
        recipeName.setText(meal.getName());

    }
}
