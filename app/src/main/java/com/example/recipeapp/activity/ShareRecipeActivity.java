package com.example.recipeapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.recipeapp.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShareRecipeActivity extends AppCompatActivity {

    Spinner spinner;
    EditText email;
    Button btn;
    FirebaseFirestore db;

    String selectedDocumentId = "";
    String recipeNameText = "";
    String ingredientNameText = "";
    String ingredinetQuantityText = "";
    String recipeTimingText = "";
    String stepsDeatilsText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_recipe);
        db = FirebaseFirestore.getInstance();

        spinner = findViewById(R.id.spinner);
        email = findViewById(R.id.emailaddress);
        btn = findViewById(R.id.shareRecipeButton);

        populateSpinnerWithRecipes();


        btn.setOnClickListener(view -> {
            String emails = email.getText().toString().trim();

            String replacestepsDeatilsText = stepsDeatilsText.replaceAll(",\\s*", "\n");
            String replacedingredientNameText = ingredientNameText.replaceAll(",\\s*", "\n");


            String body = "- Recipe Name : \n"+ recipeNameText + "\n" + "- Ingredient Name & Quantity : \n " + replacedingredientNameText  + "\n" + "- Recipe Timing : \n" + recipeTimingText + "\n" + "- Steps Details : \n" + replacestepsDeatilsText;
            String subject = "Check This Recipe -  " + recipeNameText;

            if (!emails.isEmpty()) {

                try {
                    shareDataViaEmail(subject, body, emails);

                } catch (Exception e) {
                    Log.e("TAG", "onCreate: " + e.getMessage());
                }
            }


        });


    }

    private void shareDataViaEmail(String subject, String body, String toEmail) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{toEmail});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(emailIntent, "Send Email"));

    }


    private void fetchDataFromDoc(String doc) {
        db.collection("recipes").document(doc).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {


                    if (queryDocumentSnapshots.exists()) {
                        recipeNameText = queryDocumentSnapshots.getString("recipeNameText");
                        ingredientNameText = queryDocumentSnapshots.getString("ingredientNameText");
                        ingredinetQuantityText = queryDocumentSnapshots.getString("ingredinetQuantityText");
                        recipeTimingText = queryDocumentSnapshots.getString("recipeTimingText");
                        stepsDeatilsText = queryDocumentSnapshots.getString("stepsDeatilsText");

                        Log.e("TAG2", "recipeNameText: " + recipeNameText);
                        Log.e("TAG2", "recipeTimingText: " + recipeTimingText);
                        Log.e("TAG2", "ingredientNameText: " + ingredientNameText);
                        Log.e("TAG2", "ingredinetQuantityText: " + ingredinetQuantityText);
                        Log.e("TAG2", "stepsDeatilsText: " + stepsDeatilsText);


                    }
                });
    }


    private void populateSpinnerWithRecipes() {
        db.collection("recipes").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<String> recipeNames = new ArrayList<>();
                    List<String> documentIds = new ArrayList<>();

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
                    Spinner spinnerRecipes = findViewById(R.id.spinner);
                    spinnerRecipes.setAdapter(adapter);

                    spinnerRecipes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            //   selectedDocumentId = recipeNames.get(position);
                            selectedDocumentId = documentIds.get(position);
                            Log.e("TAGPoSITION", "onItemSelected: " + selectedDocumentId);
                            fetchDataFromDoc(selectedDocumentId);
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