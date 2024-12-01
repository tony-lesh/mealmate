package com.example.mealmate.my_recipes.instruction_display;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.R;

import java.util.List;

public class InstructionAdapter extends RecyclerView.Adapter<InstructionAdapter.MyRecipeInstructionViewHolder>{

    private final List<MyRecipeInstructionBean> instructionBeanList;
    public InstructionAdapter(List<MyRecipeInstructionBean> instructionBeanList)  {
        this.instructionBeanList = instructionBeanList;
    }

    @NonNull
    @Override
    public MyRecipeInstructionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.instructions_item, parent, false);
        return new MyRecipeInstructionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecipeInstructionViewHolder holder, int position) {

        holder.bind(instructionBeanList.get(position));

    }

    @Override
    public int getItemCount() {
        return instructionBeanList.size();
    }

    public static class MyRecipeInstructionViewHolder extends RecyclerView.ViewHolder{

        TextView instructionList;

        public MyRecipeInstructionViewHolder(@NonNull View itemView) {
            super(itemView);

            instructionList = itemView.findViewById(R.id.instructionsTextView);
        }

        public void bind (MyRecipeInstructionBean instructionBean){

            instructionList.setText(instructionBean.getInstructions());
        }

    }
}
