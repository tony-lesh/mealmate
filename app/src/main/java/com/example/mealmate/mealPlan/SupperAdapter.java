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

public class SupperAdapter extends RecyclerView.Adapter<SupperAdapter.SupperViewHolder> {

    private List<Meal> supperList;
    private Context context;

    public SupperAdapter(List<Meal> supperList, Context context) {
        this.supperList = supperList;
        this.context = context;
    }

    @NonNull
    @Override
    public SupperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_meal_plan, parent, false);
        return new SupperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SupperViewHolder holder, int position) {
        Meal meal = supperList.get(position);
        holder.mealName.setText(meal.name);
        // Load image into the ImageView (replace placeholder with actual image loading library)
        Glide.with(context).load(meal.imageUrl).into(holder.mealImage);
    }

    @Override
    public int getItemCount() {
        return supperList.size();
    }

    static class SupperViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;
        ImageView mealImage;

        public SupperViewHolder(@NonNull View itemView) {
            super(itemView);

            mealImage = itemView.findViewById(R.id.supperImageView);
        }
    }
}