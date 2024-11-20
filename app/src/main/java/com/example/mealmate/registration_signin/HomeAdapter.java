package com.example.mealmate.registration_signin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.R;
import com.example.mealmate.recipe.RecipeBean;
import com.example.mealmate.recipe.UserViewHolder;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeViewHolder>{
    private final List<HomeBean> homeList;
    public HomeAdapter (List<HomeBean> homeList)  {
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "You selected, " + homeBean.getTitle(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }
}
