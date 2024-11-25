package com.example.mealmate.recipe.specificView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.R;
import com.example.mealmate.recipe.ingredients.Ingredient;
import com.example.mealmate.recipe.ingredients.SingleRecipeBean;

public class SpecificItemViewHolder extends RecyclerView.ViewHolder {

    TextView recipeName, recipeIngredients;
    ImageView recipeImage;
    public ImageView recipeAddToCart;
    public ImageView recipeLike;
    public ImageView recipeRate;

    public SpecificItemViewHolder(@NonNull View itemView) {
        super(itemView);

//        recipeImage = itemView.findViewById(R.id.recipeImage);
//        recipeName = itemView.findViewById(R.id.recipeName);
      //  recipeIngredients = itemView.findViewById(R.id.recipeIngredients);
        recipeAddToCart = itemView.findViewById(R.id.recipeAddToCart);
        recipeRate = itemView.findViewById(R.id.recipeRate);
        recipeLike = itemView.findViewById(R.id.recipeLike);
    }

    public void bind(SingleRecipeBean recipeBean) {

        if (recipeBean.getIngredients() != null && !recipeBean.getIngredients().isEmpty()) {
            StringBuilder ingredientsText = new StringBuilder();
            for (Ingredient ingredient : recipeBean.getIngredients()) {
                ingredientsText.append(ingredient.getAmount())
                        .append(" ")
                        .append(ingredient.getUnit())
                        .append(" of ")
                        .append(ingredient.getName())
                        .append("\n");
            }
            recipeIngredients.setText(ingredientsText.toString());
        } else {
            recipeIngredients.setText("No ingredients available");
        }

    }
}
