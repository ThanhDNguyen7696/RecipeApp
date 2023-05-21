package com.example.recipeapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.recipeapp.R;
import com.example.recipeapp.utils.PdfUtils;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {
    FirebaseFirestore db;
    Spinner recipeName;
    Button downloadBtn, cancelBtn;
    String ids = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        db = FirebaseFirestore.getInstance();

        recipeName = findViewById(R.id.recipeName);
        downloadBtn = findViewById(R.id.downloadBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        populateSpinnerWithRecipes();

        downloadBtn.setOnClickListener(view -> {

            if (!ids.isEmpty()) {
                fetchDataFromDoc(ids);

            }

        });

        cancelBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

    }

    private void fetchDataFromDoc(String doc) {
        db.collection("recipes").document(doc).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {


                    if (queryDocumentSnapshots.exists()) {

                        String recipeNameText = queryDocumentSnapshots.getString("recipeNameText");
                        String ingredientNameText = queryDocumentSnapshots.getString("ingredientNameText");
                        String ingredinetQuantityText = queryDocumentSnapshots.getString("ingredinetQuantityText");
                        String recipeTimingText = queryDocumentSnapshots.getString("recipeTimingText");
                        String stepsDeatilsText = queryDocumentSnapshots.getString("stepsDeatilsText");


                        String replacestepsDeatilsText = stepsDeatilsText.replaceAll(",\\s*", "\n");
                        String replacedingredientNameText = ingredientNameText.replaceAll(",\\s*", "\n");

                        String body = "- Recipe Name : \n" + recipeNameText + "\n" + "- Ingredient Name & Quantity : \n " + replacedingredientNameText + "\n" + "- Ingredient Quantity : \n" + ingredinetQuantityText + "\n" + "- Recipe Timing : \n" + recipeTimingText + "\n" + "- Steps Details : \n" + replacestepsDeatilsText;


                        Log.e("TAG2", "recipeNameText: " + recipeNameText);
                        Log.e("TAG2", "recipeTimingText: " + recipeTimingText);
                        Log.e("TAG2", "ingredientNameText: " + ingredientNameText);
                        Log.e("TAG2", "ingredinetQuantityText: " + ingredinetQuantityText);
                        Log.e("TAG2", "stepsDeatilsText: " + stepsDeatilsText);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            PdfUtils.generatePdfFromText(this, body, recipeNameText);
                        }


                    }
                });
    }


    private void populateSpinnerWithRecipes() {
        db.collection("recipes").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<String> recipeNames = new ArrayList<>();
                    List<String> documentIds = new ArrayList<>();
                    List<String> Direction = new ArrayList<>();

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        if (documentSnapshot.exists()) {
                            String recipeName = documentSnapshot.getString("recipeNameText");
                            String documentId = documentSnapshot.getString("documentId");

                            recipeNames.add(recipeName);
                            documentIds.add(documentId);
                        }
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, recipeNames);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Spinner spinnerRecipes = findViewById(R.id.recipeName);
                    spinnerRecipes.setAdapter(adapter);


                    spinnerRecipes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            ids = documentIds.get(position);


                            // Use the selectedRecipeName and selectedDocumentId as needed
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }


                    });
                })

                .addOnFailureListener(e -> {
                    // Handle any errors
                });

    }

}