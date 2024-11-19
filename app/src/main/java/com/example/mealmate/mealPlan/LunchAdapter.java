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

public class LunchAdapter extends RecyclerView.Adapter<LunchAdapter.LunchViewHolder> {

    private List<Meal> lunchList;
    private Context context;

    public LunchAdapter(List<Meal> lunchList, Context context) {
        this.lunchList = lunchList;
        this.context = context;
    }

    @NonNull
    @Override
    public LunchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_meal_plan, parent, false);
        return new LunchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LunchViewHolder holder, int position) {
        Meal meal = lunchList.get(position);
        holder.mealName.setText(meal.name);
        // Load image into the ImageView (replace placeholder with actual image loading library)
        Glide.with(context).load(meal.imageUrl).into(holder.mealImage);
    }

    @Override
    public int getItemCount() {
        return lunchList.size();
    }

    static class LunchViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;
        ImageView mealImage;

        public LunchViewHolder(@NonNull View itemView) {
            super(itemView);
           // mealName = itemView.findViewById(R.id.meal_name);
            mealImage = itemView.findViewById(R.id.lunchImageView);
        }
    }
}
