package com.example.mealmate.recipe.ingredients;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.R;
import com.example.mealmate.recipe.specificView.SpecificItemActivity;
import com.example.mealmate.shoppingCart.Shopping_Cart;
import com.example.mealmate.shoppingCart.groceryList.GroceryListActivity;

import java.io.Serializable;
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

//        holder.recipeAddToCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), "Added to cart", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(v.getContext(), Shopping_Cart.class);
//                intent.putExtra("id", ingredient.getName()); // Pass recipe ID
//                intent.putExtra("recipeName", ingredient.getAmount());
//                intent.putExtra("recipeImage", ingredient.getUnit());
//                v.getContext().startActivity(intent);
//            }
//        });

    }
    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientTextView;
        ImageView recipeAddToCart, recipeRate, recipeLike;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientTextView = itemView.findViewById(R.id.recipeIngredients);
            recipeAddToCart = itemView.findViewById(R.id.recipeAddToCart);
            recipeRate = itemView.findViewById(R.id.recipeRate);
            recipeLike = itemView.findViewById(R.id.recipeLike);
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

