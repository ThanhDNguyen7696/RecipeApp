package com.example.recipeapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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
import java.util.Arrays;
import java.util.List;

public class SearchDirectionActivity extends AppCompatActivity {
    Spinner recipeSpinner;
    Spinner ingredientSpinner;
    Button btn;
    FirebaseFirestore db;
    String directionSteps = "";
    TextView stepsDetails,ingredientName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_direction);
        db = FirebaseFirestore.getInstance();

        recipeSpinner = findViewById(R.id.recipSpinner);
        ingredientSpinner = findViewById(R.id.ingredientSpinner);
        btn = findViewById(R.id.collectionbtn);
        stepsDetails = findViewById(R.id.stepsDetails);
        ingredientName = findViewById(R.id.ingredientName);

        ingredientName.setVisibility(View.GONE);


        populateSpinnerWithRecipes();

        btn.setOnClickListener(view -> {
            if(!directionSteps.isEmpty()){
                ingredientName.setVisibility(View.VISIBLE);

                String replacedString = directionSteps.replaceAll(",\\s*", "\n");
                Log.e("TAGs", "onCreate: "+replacedString );

                stepsDetails.setText(replacedString);
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
                            String Directions= documentSnapshot.getString("stepsDeatilsText");

                            recipeNames.add(recipeName);
                            ingredientname.add(ingredientNameText);
                            Direction.add(Directions);
                        }
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, recipeNames);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Spinner spinnerRecipes = findViewById(R.id.recipSpinner);
                    spinnerRecipes.setAdapter(adapter);



                    spinnerRecipes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            directionSteps = Direction.get(position);

                            String ingredie = ingredientname.get(position);
                            List<String> itemList = Arrays.asList(ingredie.split(","));

//                            ingredientName.setText(ingredie);
                            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(SearchDirectionActivity.this, android.R.layout.simple_spinner_item, itemList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            Spinner spinnerRecipes1 = findViewById(R.id.ingredientSpinner);
                            spinnerRecipes1.setAdapter(adapter1);


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