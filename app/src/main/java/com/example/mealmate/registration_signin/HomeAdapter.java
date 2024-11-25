package com.example.mealmate.registration_signin;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.R;

import com.example.mealmate.recipe.specificView.SpecificItemActivity;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeViewHolder>{
    private final List<HomeBean> homeList;
    public HomeAdapter (LandingPageActivity landingPageActivity, List<HomeBean> homeList)  {
        this.homeList =homeList;
    }
    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.landing_page_item, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.bind(homeList.get(position));
        HomeBean homeBean = homeList.get(position);
        holder.itemView.setOnClickListener(v -> {
            // Log to check the ID value before passing it
            Log.d("HomeBean", "Selected Recipe ID: " + homeBean.getId());

            // Check if the ID is valid before proceeding
            if (homeBean.getId() != -1) {
                Toast.makeText(v.getContext(), "You selected, " + homeBean.getTitle(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(v.getContext(), SpecificItemActivity.class);
                intent.putExtra("id", homeBean.getId()); // Pass recipe ID
                intent.putExtra("recipeName", homeBean.getTitle());
                intent.putExtra("recipeImage", homeBean.getImageURL());
                intent.putExtra("recipeIngredients", homeBean.getDescription());
                v.getContext().startActivity(intent);
            } else {
                Toast.makeText(v.getContext(), "Invalid Recipe ID", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }
}
