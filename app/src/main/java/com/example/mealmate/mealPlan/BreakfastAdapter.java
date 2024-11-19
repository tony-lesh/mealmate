package com.example.mealmate.mealPlan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealmate.R;

import java.util.List;

public class BreakfastAdapter extends RecyclerView.Adapter<BreakfastAdapter.BreakfastViewHolder> {

    private List<Meal> breakfastList;
    private Context context;

    public BreakfastAdapter(List<Meal> breakfastList, Context context) {
        this.breakfastList = breakfastList;
        this.context = context;
    }

    @NonNull
    @Override
    public BreakfastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_meal_plan, parent, false);
        return new BreakfastViewHolder(view);
    }

//    @NonNull
//    @Override
//    public BreakfastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
//    }

    @Override
    public void onBindViewHolder(@NonNull BreakfastViewHolder holder, int position) {
        Meal meal = breakfastList.get(position);
        holder.mealName.setText(meal.name);
        // Load image into the ImageView (replace placeholder with actual image loading library)
        Glide.with(context).load(meal.imageUrl).into(holder.mealImage);
    }

    @Override
    public int getItemCount() {
        return breakfastList.size();
    }

    static class BreakfastViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;
        ImageView mealImage;

        public BreakfastViewHolder(@NonNull View itemView) {
            super(itemView);
           // mealName = itemView.findViewById(R.id.meal_name);
            mealImage = itemView.findViewById(R.id.breakfastImageView);
        }
    }
}