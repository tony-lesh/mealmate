package com.example.mealmate.recipe.ingredients;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.R;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private final Context context;
    private final List<Ingredient> ingredients;

    public IngredientAdapter(Context context, List<Ingredient> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_recipe_item, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        holder.bind(ingredients.get(position));
        Ingredient ingredient = ingredients.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "You selected, " + ingredient.getName(), Toast.LENGTH_LONG).show();

            }
        });

    }
    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientTextView;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientTextView = itemView.findViewById(R.id.recipeIngredients);
        }

            public void bind(Ingredient ingredient) {
                // Single ingredient, no need to loop
                if (ingredient.getName() != null && !ingredient.getName().isEmpty()) {
                    String ingredientText = ingredient.getAmount() + " " +
                            ingredient.getUnit() + " of " +
                            ingredient.getName();
                    ingredientTextView.setText(ingredientText);
                } else {
                    ingredientTextView.setText("No ingredients available");
                }
            }
    }
}

