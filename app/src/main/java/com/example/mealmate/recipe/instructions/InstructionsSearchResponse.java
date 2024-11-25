package com.example.mealmate.recipe.instructions;

import com.example.mealmate.recipe.ingredients.Ingredient;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InstructionsSearchResponse {
    @SerializedName("instructions")
    private List<Instruction> results;

    public List<Instruction> getInstructions(){
        return results;
    }


}
