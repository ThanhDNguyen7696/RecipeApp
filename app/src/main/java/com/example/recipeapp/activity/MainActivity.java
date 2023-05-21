package com.example.recipeapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.recipeapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    CardView createRecipe;
    CardView shareRecipe;
    CardView searchDirection;
    CardView searchIngredient;
    CardView downloadRecipe;
    TextView signout;
    private FirebaseAuth mAuth;
    private SharedPreferences mpref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createRecipe = findViewById(R.id.createRecipeCard);
        shareRecipe = findViewById(R.id.shareRecipeCard);
        searchDirection = findViewById(R.id.searchDirectionCard);
        searchIngredient = findViewById(R.id.searchIngredientCard);
        downloadRecipe = findViewById(R.id.downloadRecipeCard);
        signout = findViewById(R.id.signout);

        mAuth = FirebaseAuth.getInstance();
        mpref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        signout.setOnClickListener(view -> {

            mAuth.signOut();
            SharedPreferences.Editor editor = mpref.edit().clear();
            editor.apply();

            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        });

        createRecipe.setOnClickListener(view -> {

            Intent intent = new Intent(this,CreateRecipeActivity.class);
            startActivity(intent);

        });

        shareRecipe.setOnClickListener(view -> {

            Intent intent = new Intent(this,ShareRecipeActivity.class);
            startActivity(intent);

        });

        searchDirection.setOnClickListener(view -> {

            Intent intent = new Intent(this,SearchDirectionActivity.class);
            startActivity(intent);

        });

        searchIngredient.setOnClickListener(view -> {

            Intent intent = new Intent(this,SearchIngredientActivity.class);
            startActivity(intent);

        });

        downloadRecipe.setOnClickListener(view -> {

            Intent intent = new Intent(this,RecipeDetailActivity.class);
            startActivity(intent);

        });


    }
}