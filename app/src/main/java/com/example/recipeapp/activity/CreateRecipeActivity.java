package com.example.recipeapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.recipeapp.R;
import com.example.recipeapp.adapter.IngredientAdapter;
import com.example.recipeapp.model.IngredientModel;
import com.example.recipeapp.model.RecipeModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class CreateRecipeActivity extends AppCompatActivity {

    EditText recipeName,recipeTiming,ingredientName,ingredinetQuantity,stepsDeatils;
    Button createRecipe,addIngredientButton,addStepstButton;
    FirebaseFirestore db;
    RecyclerView recyclerViewIngredient;
    RecyclerView recyclerViewSteps;
    String ingName ="";
    String stepsdDetails ="";

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        db = FirebaseFirestore.getInstance();

        recipeName = findViewById(R.id.recipeName);
        recipeTiming = findViewById(R.id.recipeTiming);
        ingredientName = findViewById(R.id.ingredientName);
        ingredinetQuantity = findViewById(R.id.ingredinetQuantity);
        stepsDeatils = findViewById(R.id.stepsDeatils);
        createRecipe = findViewById(R.id.createRecipe);
        addIngredientButton = findViewById(R.id.addIngredientButton);
        addStepstButton = findViewById(R.id.addStepstButton);
        recyclerViewIngredient = findViewById(R.id.recyclerViewIngredient);
        recyclerViewSteps = findViewById(R.id.recyclerViewSteps);
        progressBar = findViewById(R.id.progressBar);

        ArrayList<IngredientModel> ingredientNameList = new ArrayList<IngredientModel>();
        ArrayList<IngredientModel> ingredientStepsList = new ArrayList<IngredientModel>();



        addIngredientButton.setOnClickListener(view1 -> {

            String ingredientNameText     = ingredientName.getText().toString().trim();
            String ingredinetQuantityText  = ingredinetQuantity.getText().toString().trim();


            ingredientNameList.add(new IngredientModel(ingredientNameText,ingredinetQuantityText));
            ingName+=ingredientNameText+" - "+ingredinetQuantityText+",";
            ingredientName.setText("");
            ingredinetQuantity.setText("");

            if(!ingredientNameText.isEmpty() && !ingredinetQuantityText.isEmpty()){
                recyclerViewIngredient.setAdapter(new IngredientAdapter( ingredientNameList));


            }else {
                Toast.makeText(this, "Please Fill ingredient", Toast.LENGTH_SHORT).show();

            }

        });




        addStepstButton.setOnClickListener(view1 -> {

            String stepsDeatilsText = stepsDeatils.getText().toString().trim();

            ingredientStepsList.add(new IngredientModel(stepsDeatilsText,""));
            stepsdDetails+=stepsDeatilsText+",";
            stepsDeatils.setText("");
         ;

         if(!stepsDeatilsText.isEmpty()){
             recyclerViewSteps.setAdapter(new IngredientAdapter( ingredientStepsList));
            // Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();


         }else{
            Toast.makeText(this, "Please Fill Steps to Add", Toast.LENGTH_SHORT).show();

         }

            //Toast.makeText(this, ""+ingredientNameText +" "+ingredinetQuantityText, Toast.LENGTH_SHORT).show();

        });





        createRecipe.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);

          //  Toast.makeText(this, "ingName" +ingName, Toast.LENGTH_SHORT).show();

            String recipeNameText        = recipeName.getText().toString().trim();
            String recipeTimingText       = recipeTiming.getText().toString().trim();
            String ingredientNameText     = ingredientName.getText().toString().trim();
            String ingredinetQuantityText  = ingredinetQuantity.getText().toString().trim();
            String stepsDeatilsText        = stepsDeatils.getText().toString().trim();

            if(!recipeNameText.isEmpty()&&!recipeTimingText.isEmpty()&&!ingName.isEmpty()&&!stepsdDetails.isEmpty()){
                String documentId = db.collection("recipes").document().getId();


            Log.e("TAG1", "recipeNameText: "+recipeNameText );
            Log.e("TAG1", "recipeTimingText: "+recipeTimingText );
            Log.e("TAG1", "ingredientNameText: "+ingredientNameText );
            Log.e("TAG1", "ingredinetQuantityText: "+ingredinetQuantityText );
            Log.e("TAG1", "stepsDeatilsText: "+stepsDeatilsText );
            Log.e("TAG1", "documentId: "+documentId );

            RecipeModel model =  new RecipeModel(recipeNameText,recipeTimingText,ingName,ingredinetQuantityText,stepsdDetails);


                data(model,documentId);
            }else{
                progressBar.setVisibility(View.GONE);

                Toast.makeText(this, "Please Fill All Field", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void  data(RecipeModel model,String docid){
        db.collection("recipes").document(docid).set(model)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Recipe Created in DataBase", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                    db.collection("recipes").document(docid).update("documentId",docid);

                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    // Data set successfully
                })
                .addOnFailureListener(e -> {
                    // Handle any errors
                });
    }
/*
    private void data(RecipeModel model) {
        db.collection("recipes").add(model)
                .addOnSuccessListener(documentReference -> {
                    String documentId = documentReference.getId();
                    Toast.makeText(this, "Recipe Created in Database", Toast.LENGTH_SHORT).show();
                    Log.e("TAG1", "documentId: " + documentId);

                    // Update the document with the generated document ID
                    documentReference.update("documentId", documentId)
                            .addOnSuccessListener(aVoid -> {
                                Log.e("TAG1", "Recipe document updated with documentId: " + documentId);

                                Intent intent = new Intent(this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            })
                            .addOnFailureListener(e -> {
                                // Handle any errors while updating the document
                            });
                })
                .addOnFailureListener(e -> {
                    // Handle any errors while adding the document
                });
    }
*/

}