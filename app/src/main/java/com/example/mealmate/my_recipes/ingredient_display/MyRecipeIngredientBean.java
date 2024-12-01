package com.example.mealmate.my_recipes.ingredient_display;

public class MyRecipeIngredientBean {
    String id;
    String ingredients;

    public MyRecipeIngredientBean() {
    }

    public MyRecipeIngredientBean(String id, String ingredients) {
        this.id = id;
        this.ingredients = ingredients;
    }

    public String getId() {
        return id;
    }

    public String getIngredients() {
        return ingredients;
    }

    @Override
    public String toString() {
        return "MyRecipeIngredientBean{" +
                "id=" + id +
                ", ingredients='" + ingredients + '\'' +
                '}';
    }
}
