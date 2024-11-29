package com.example.mealmate.mealPlan.dailyMealPlan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.R;
import com.example.mealmate.recipe.ingredients.Ingredient;
import com.example.mealmate.recipe.ingredients.IngredientAdapter;

import java.util.List;

public class DailyPlanAdapter extends RecyclerView.Adapter<DailyPlanAdapter.DailyPlanViewHolder> {
    private final Context context;
    private final List<DailyPlanBean> dailyPlanBeans;

    public DailyPlanAdapter(Context context, List<DailyPlanBean> dailyPlanBeans) {
        this.context = context;
        this.dailyPlanBeans = dailyPlanBeans;
    }

    @NonNull
    @Override
    public DailyPlanAdapter.DailyPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.daily_meal_plan_item, parent, false);
        return new DailyPlanAdapter.DailyPlanViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull DailyPlanAdapter.DailyPlanViewHolder holder, int position) {

        holder.bind(dailyPlanBeans.get(position));
        DailyPlanBean dailyPlanBean = dailyPlanBeans.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "You selected, " + dailyPlanBean.getTitle(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dailyPlanBeans.size();
    }

    public static class DailyPlanViewHolder extends RecyclerView.ViewHolder{
        TextView nutritionTitle, nutritionMeal;
        ImageView recipeAddToCart, recipeRate, recipeLike;

        public DailyPlanViewHolder(@NonNull View itemView) {
            super(itemView);

            nutritionTitle = itemView.findViewById(R.id.nutritionTitle);
            nutritionMeal = itemView.findViewById(R.id.nutritionMeal);

            recipeLike = itemView.findViewById(R.id.recipeLike);
            recipeRate = itemView.findViewById(R.id.recipeRate);
            recipeAddToCart = itemView.findViewById(R.id.recipeAddToCart);
        }

        public void bind(DailyPlanBean dailyPlanBean){

            // Single ingredient, no need to loop
            if (dailyPlanBean.getTitle() != null && !dailyPlanBean.getTitle().isEmpty()) {
                String dailyPlanText = dailyPlanBean.getReadyInMinutes() + "minutes\n" +
                        dailyPlanBean.getServings() + " servings\n " +
                        dailyPlanBean.getSourceUrl();
                nutritionMeal.setText(dailyPlanText);
            } else {
                nutritionMeal.setText("No summary available");
            }

            nutritionTitle.setText(dailyPlanBean.getTitle());

        }
    }
}
