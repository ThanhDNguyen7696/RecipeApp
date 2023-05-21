package com.example.recipeapp.model;

public class IngredientModel {
    String ingredientName;
    String ingredinetQuantity;

    public String getIngredientName() {
        return ingredientName;
    }

    public String getIngredinetQuantity() {
        return ingredinetQuantity;
    }

    public IngredientModel(String ingredientName, String ingredinetQuantity) {
        this.ingredientName = ingredientName;
        this.ingredinetQuantity = ingredinetQuantity;
    }
}
