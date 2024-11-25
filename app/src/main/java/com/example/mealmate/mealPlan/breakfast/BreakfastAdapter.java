package com.example.mealmate.mealPlan.breakfast;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.R;
import com.example.mealmate.mealPlan.Meal;

import java.util.List;

public class BreakfastAdapter extends RecyclerView.Adapter<BreakfastViewHolder>{
    private final List<Meal> breakfastMeal;
    public BreakfastAdapter (List<Meal> breakfastMeal)  {
        this.breakfastMeal =breakfastMeal;
    }

    @NonNull
    @Override
    public BreakfastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_plan_item, parent, false);
        return new BreakfastViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BreakfastViewHolder holder, int position) {
        holder.bind(breakfastMeal.get(position));
        Meal mealBreakfast =breakfastMeal.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "You selected, " + mealBreakfast.getName(), Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return breakfastMeal.size();
    }
}