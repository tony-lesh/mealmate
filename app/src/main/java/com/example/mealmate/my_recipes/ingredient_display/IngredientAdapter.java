package com.example.mealmate.my_recipes.ingredient_display;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.R;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.MyRecipeIngredientViewHolder>{

    private final List<MyRecipeIngredientBean> ingredientBeanList;
    public IngredientAdapter (List<MyRecipeIngredientBean> ingredientBeanList)  {
        this.ingredientBeanList = ingredientBeanList;
    }


    @NonNull
    @Override
    public MyRecipeIngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_item, parent, false);
        return new IngredientAdapter.MyRecipeIngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecipeIngredientViewHolder holder, int position) {

        holder.bind(ingredientBeanList.get(position));
//        MyRecipeIngredientBean ingredientBean = ingredientBeanList.get(position);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return ingredientBeanList.size();
    }

    public static class MyRecipeIngredientViewHolder extends RecyclerView.ViewHolder{

        TextView ingredientList;

        public MyRecipeIngredientViewHolder(@NonNull View itemView) {
            super(itemView);

            ingredientList = itemView.findViewById(R.id.ingredientsTextView);
        }

        public void bind (MyRecipeIngredientBean ingredientBean){

            ingredientList.setText(ingredientBean.getIngredients());
        }

    }
}
