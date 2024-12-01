package com.example.mealmate.my_recipes.instruction_display;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

public class InstructionFragment extends Fragment {

    private RecyclerView recyclerView;
    private InstructionAdapter instructionAdapter;
    private List<MyRecipeInstructionBean> myRecipeInstructionsBeans;
    private DatabaseReference dbRef;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_instructions, container, false);

        recyclerView = view.findViewById(R.id.instructionsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setLayoutManager(new GridLayoutManager(this, ));


        myRecipeInstructionsBeans = new ArrayList<>();
        instructionAdapter = new InstructionAdapter(myRecipeInstructionsBeans);
        recyclerView.setAdapter(instructionAdapter);

        dbRef = FirebaseDatabase.getInstance().getReference("My_Recipes");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myRecipeInstructionsBeans.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    MyRecipeInstructionBean recipeBean = dataSnapshot.getValue(MyRecipeInstructionBean.class);
                    myRecipeInstructionsBeans.add(recipeBean);
                }
                instructionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load data!", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    

}
