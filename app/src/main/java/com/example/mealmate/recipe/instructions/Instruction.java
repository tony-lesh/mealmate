package com.example.mealmate.recipe.instructions;

import com.google.gson.annotations.SerializedName;

public class Instruction {

    @SerializedName("name")
    private int step;

    @SerializedName("steps")
    private String description;


    public Instruction(int step, String description) {
        this.step = step;
        this.description = description;
    }

    public Instruction() {
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

    @Override
    public String toString() {
        return "Instruction{" +
                "step=" + step +
                ", description='" + description + '\'' +
                '}';
    }
}
