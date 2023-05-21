package com.example.recipeapp.model;

public class RecipeModel {
    String recipeNameText;
    String recipeTimingText;
    String ingredientNameText;
    String ingredinetQuantityText;
    String stepsDeatilsText  ;

    public RecipeModel(String recipeNameText, String recipeTimingText, String ingredientNameText, String ingredinetQuantityText, String stepsDeatilsText) {
        this.recipeNameText = recipeNameText;
        this.recipeTimingText = recipeTimingText;
        this.ingredientNameText = ingredientNameText;
        this.ingredinetQuantityText = ingredinetQuantityText;
        this.stepsDeatilsText = stepsDeatilsText;


    }

    public String getRecipeNameText() {
        return recipeNameText;
    }

    public String getRecipeTimingText() {
        return recipeTimingText;
    }

    public String getIngredientNameText() {
        return ingredientNameText;
    }

    public String getIngredinetQuantityText() {
        return ingredinetQuantityText;
    }

    public String getStepsDeatilsText() {
        return stepsDeatilsText;
    }

}

