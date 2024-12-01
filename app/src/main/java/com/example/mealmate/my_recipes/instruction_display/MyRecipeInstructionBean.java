package com.example.mealmate.my_recipes.instruction_display;

public class MyRecipeInstructionBean {
    String id;
    String instructions;

    public MyRecipeInstructionBean() {
    }

    public MyRecipeInstructionBean(String id, String instructions) {
        this.id = id;
        this.instructions = instructions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return "MyRecipeInstructionBean{" +
                "id=" + id +
                ", instructions='" + instructions + '\'' +
                '}';
    }
}
