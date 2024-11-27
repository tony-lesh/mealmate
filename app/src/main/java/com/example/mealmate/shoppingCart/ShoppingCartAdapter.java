package com.example.mealmate.shoppingCart;

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

import com.bumptech.glide.Glide;
import com.example.mealmate.R;
import com.example.mealmate.shoppingCart.groceryList.GroceryListActivity;

import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ShoppingCartViewHolder> {

    private final Context context;
    private final List<ShoppingCartBean> shoppingCartItems;

    public ShoppingCartAdapter(Context context, List<ShoppingCartBean> shoppingCartItems) {
        this.context = context;
        this.shoppingCartItems = shoppingCartItems;
    }

    @NonNull
    @Override
    public ShoppingCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shopping_cart, parent, false);
        return new ShoppingCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCartViewHolder holder, int position) {


        ShoppingCartBean item = shoppingCartItems.get(position);
        holder.bindData(item);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Added to grocery list", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(v.getContext(), GroceryListActivity.class);
                intent.putExtra("id", item.getRecipeId()); // Pass recipe ID
                intent.putExtra("name", item.getRecipeName());
                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return shoppingCartItems.size();
    }

    public static class ShoppingCartViewHolder extends RecyclerView.ViewHolder {
        ImageView recipeImageView, delete, payment;
        TextView recipeNameTextView;

        public ShoppingCartViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeImageView = itemView.findViewById(R.id.recipe);
            recipeNameTextView = itemView.findViewById(R.id.recipeName);
            delete = itemView.findViewById(R.id.deleteView);
            payment = itemView.findViewById(R.id.payView);
        }

        // Method to bind data to the views
        public void bindData(ShoppingCartBean item) {
            // Set the recipe name
            recipeNameTextView.setText(item.getRecipeName());

            // Use Glide to load the recipe image into the ImageView
            Glide.with(itemView.getContext())
                    .load(item.getRecipeImage())
                    .into(recipeImageView);
        }

    }
}

