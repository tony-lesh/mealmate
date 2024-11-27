package com.example.mealmate.recipe.instructions;

import com.google.gson.annotations.SerializedName;

public class Instruction {

    @SerializedName("id")
    private int id;

    @SerializedName("step")
    private int step;

    @SerializedName("name")
    private String description;


    public Instruction(int id, int step, String description) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "id=" + id +
                ", step=" + step +
                ", description='" + description + '\'' +
                '}';
    }
}
