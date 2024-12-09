package com.example.mealmate.recipe;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mealmate.R;
import com.example.mealmate.recipe.ingredients.SingleRecipeBean;
import com.example.mealmate.recipe.specificView.SpecificItemActivity;
import com.example.mealmate.shoppingCart.Shopping_Cart;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {
    private final List<RecipeBean> userList;
    public UserAdapter (List<RecipeBean> userList)  {
        this.userList =userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bind(userList.get(position));
        RecipeBean recipeBean = userList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "You selected, " + recipeBean.getTitle(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(v.getContext(), SpecificItemActivity.class);
                intent.putExtra("id", recipeBean.getId()); // Pass recipe ID
                intent.putExtra("recipeName", recipeBean.getTitle());
                intent.putExtra("recipeImage", recipeBean.getImageURL());
                intent.putExtra("recipeIngredients", recipeBean.getDescription());
                v.getContext().startActivity(intent);

            }
        });

        holder.recipeAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Added to cart", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(v.getContext(), Shopping_Cart.class);
                intent.putExtra("id", recipeBean.getId()); // Pass recipe ID
                intent.putExtra("recipeName", recipeBean.getTitle());
                intent.putExtra("recipeImage", recipeBean.getImageURL());
                v.getContext().startActivity(intent);
            }
        });

        holder.recipeRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Rate out of 5", Toast.LENGTH_LONG).show();
            }
        });

        holder.recipeLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Added to Favorites!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
