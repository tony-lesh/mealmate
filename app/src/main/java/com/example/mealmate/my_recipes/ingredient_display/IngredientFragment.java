package com.example.mealmate.my_recipes.ingredient_display;

import android.graphics.Insets;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class IngredientFragment extends Fragment {

    private RecyclerView recyclerView;
    private IngredientAdapter ingredientAdapter;
    private List<MyRecipeIngredientBean> myRecipeIngredientBeans;
    private DatabaseReference dbRef;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);

        recyclerView = view.findViewById(R.id.ingredientsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setLayoutManager(new GridLayoutManager(this, ));


        myRecipeIngredientBeans = new ArrayList<>();
        ingredientAdapter = new IngredientAdapter(myRecipeIngredientBeans);
        recyclerView.setAdapter(ingredientAdapter);

        dbRef = FirebaseDatabase.getInstance().getReference("My_Recipes");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myRecipeIngredientBeans.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    MyRecipeIngredientBean recipeBean = dataSnapshot.getValue(MyRecipeIngredientBean.class);
                    myRecipeIngredientBeans.add(recipeBean);
                }
                ingredientAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load data!", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    

}
