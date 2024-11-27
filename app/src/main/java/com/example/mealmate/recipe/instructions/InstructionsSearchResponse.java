package com.example.mealmate.recipe.instructions;

import com.example.mealmate.recipe.ingredients.Ingredient;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InstructionsSearchResponse {
    @SerializedName("parsedInstructions")
    private List<Instruction> results;

    public List<Instruction> getInstructions(){
        return results;
    }

    public static class Result{
        @SerializedName("id")
        private int id;

        @SerializedName("step")
        private int step;

        @SerializedName("name")
        private String description;

        public Result() {
        }

        public Result(int id, int step, String description) {
            this.id = id;
            this.step = step;
            this.description = description;
        }

        public int getStep() {
            return step;
        }

        public void setStep(int step) {
            this.step = step;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "id=" + id +
                    ", step=" + step +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
