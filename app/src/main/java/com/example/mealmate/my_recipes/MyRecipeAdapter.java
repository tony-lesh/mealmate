package com.example.mealmate.my_recipes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealmate.R;
import com.example.mealmate.recipe.ingredients.IngredientAdapter;
import com.example.mealmate.recipe.specificView.SpecificItemActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MyRecipeAdapter extends RecyclerView.Adapter<MyRecipeAdapter.MyRecipeViewHolder>{

    private final List<MyRecipeBean> recipeBeanList;
    public MyRecipeAdapter (List<MyRecipeBean> recipeBeanList)  {
        this.recipeBeanList =recipeBeanList;
    }

    @NonNull
    @Override
    public MyRecipeAdapter.MyRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_recipes_item, parent, false);
        return new MyRecipeViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyRecipeAdapter.MyRecipeViewHolder holder, int position) {

        holder.bind(recipeBeanList.get(position));
        MyRecipeBean myRecipeBean = recipeBeanList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(v.getContext(), "You selected, " + myRecipeBean.getTitle(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(v.getContext(), ExtentionRecipeDetailsActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        holder.editImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.deleteImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();

                new AlertDialog.Builder(v.getContext())
                        .setTitle("Confirm Delete")
                        .setMessage("Are you sure you want to delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Delete the user from Firebase database
                                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("My_Recipes").child(myRecipeBean.getId()); // Assuming User object has a userId

                                userRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Remove the item from the list and notify the adapter
                                           recipeBeanList.remove(position);
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, recipeBeanList.size());
                                            Toast.makeText(v.getContext(), "Delete Successful", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(v.getContext(), "Delete Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", null)
                        .setIcon(R.drawable.ic_delete)
                        .show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return recipeBeanList.size();
    }


    public static class MyRecipeViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView;
        ImageView recipeImageView;
        ImageButton editImageBtn, deleteImageBtn;

        public MyRecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.txtRecipeTitle);
            recipeImageView = itemView.findViewById(R.id.recipeImage);
            editImageBtn = itemView.findViewById(R.id.editBtn);
            deleteImageBtn = itemView.findViewById(R.id.deleteBtn);

        }

        public void bind (MyRecipeBean myRecipeBean){

            // Load image using Glide or Picasso
            Glide.with(itemView.getContext())
                    .load(myRecipeBean.getImageURL())
                    .into(recipeImageView);
            titleTextView.setText(myRecipeBean.getTitle());
        }
    }
}
