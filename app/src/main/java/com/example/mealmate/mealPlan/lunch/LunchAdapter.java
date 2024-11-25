package com.example.mealmate.mealPlan.lunch;

import android.content.Context;
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
import com.example.mealmate.mealPlan.Meal;
import com.example.mealmate.mealPlan.breakfast.BreakfastViewHolder;

import java.util.List;

public class LunchAdapter extends RecyclerView.Adapter<LunchViewHolder> {

    private final List<Meal> lunchMeal;
    public LunchAdapter (List<Meal> lunchMeal)  {
        this.lunchMeal =lunchMeal;
    }

    @NonNull
    @Override
    public LunchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_plan_item, parent, false);
        return new LunchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LunchViewHolder holder, int position) {
        holder.bind(lunchMeal.get(position));
        Meal mealBreakfast =lunchMeal.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "You selected, " + mealBreakfast.getName(), Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return lunchMeal.size();
    }
}
