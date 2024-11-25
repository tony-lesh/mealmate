package com.example.mealmate.recipe.instructions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.R;
import com.example.mealmate.recipe.ingredients.Ingredient;
import com.example.mealmate.recipe.ingredients.IngredientAdapter;

import java.util.List;

public class InstructionsAdapter extends RecyclerView.Adapter<InstructionsAdapter.InstructionsViewHolder> {

    private final Context context;
    private final List<Instruction> instructions;

    public InstructionsAdapter(Context context, List<Instruction> instructions) {
        this.context = context;
        this.instructions = instructions;
    }

    @NonNull
    @Override
    public InstructionsAdapter.InstructionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_recipe_item, parent, false);
        return new InstructionsAdapter.InstructionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsAdapter.InstructionsViewHolder holder, int position) {
        holder.bind(instructions.get(position));
        Instruction instruction = instructions.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "You selected, " + instruction.getStep(), Toast.LENGTH_LONG).show();

            }
        });

    }
    @Override
    public int getItemCount() {
        return instructions.size();
    }

    public static class InstructionsViewHolder extends RecyclerView.ViewHolder {

        TextView instructionsTextView;

        public InstructionsViewHolder(@NonNull View itemView) {
            super(itemView);
            instructionsTextView = itemView.findViewById(R.id.recipeInstructions);
        }

        public void bind(Instruction instruction) {

            // StringBuilder to build the instructions text
            StringBuilder instructionsText = new StringBuilder();

                // Check if the step value is a valid number (e.g., step >= 0 or a valid range)
                if (instruction.getStep() >= 0) {
                    instructionsText.append("Step ").append(instruction.getStep())
                            .append(": ").append(instruction.getDescription())
                            .append("\n");
                } else {
                    instructionsText.append("Invalid instruction\n\n");
                }
            // Set the formatted text to the TextView
            instructionsTextView.setText(instructionsText.toString());


        }
    }
}