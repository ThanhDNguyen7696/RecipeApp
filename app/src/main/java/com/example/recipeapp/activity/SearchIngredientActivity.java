package com.example.recipeapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.recipeapp.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchIngredientActivity extends AppCompatActivity {

    Spinner chooseRecipeName;
    Button collectIngredientBtn;
    FirebaseFirestore db;
    TextView collectIngredint;
    String ingrednt ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_ingredient);
        db = FirebaseFirestore.getInstance();


        populateSpinnerWithRecipes();
        chooseRecipeName= findViewById(R.id.recipeName);
        collectIngredint= findViewById(R.id.collectIngredint);
        collectIngredientBtn= findViewById(R.id.collectIngredient);


        collectIngredientBtn.setOnClickListener(view -> {

            if(!ingrednt.isEmpty()){

                String replacedString = ingrednt.replaceAll(",\\s*", "\n");

                collectIngredint.setText(replacedString);

            }
        });



    }
    private void populateSpinnerWithRecipes() {
        db.collection("recipes").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<String> recipeNames = new ArrayList<>();
                    List<String> ingredientname = new ArrayList<>();
                    List<String> Direction = new ArrayList<>();

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        if (documentSnapshot.exists()) {
                            String recipeName = documentSnapshot.getString("recipeNameText");
                            String ingredientNameText = documentSnapshot.getString("ingredientNameText");

                            recipeNames.add(recipeName);
                            ingredientname.add(ingredientNameText);
                        }
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, recipeNames);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Spinner spinnerRecipes = findViewById(R.id.recipeName);
                    spinnerRecipes.setAdapter(adapter);



                    spinnerRecipes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            ingrednt = ingredientname.get(position);


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