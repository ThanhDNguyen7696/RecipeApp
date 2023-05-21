package com.example.recipeapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipeapp.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateActivity extends AppCompatActivity {

    Button createUserBtn;
    EditText email;
    EditText password;
    TextView goToLogin;
    private FirebaseAuth mAuth;
    private SharedPreferences mpref;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        createUserBtn = findViewById(R.id.createUserBtn);
        email = findViewById(R.id.emailaddress);
        password = findViewById(R.id.psssword);
        goToLogin = findViewById(R.id.gotologin);
        progressBar = findViewById(R.id.progressBar);
        FirebaseApp.initializeApp(this); // Initialize FirebaseApp
        mpref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);


        mAuth = FirebaseAuth.getInstance();



        goToLogin.setOnClickListener(view -> {
            Intent intent =new Intent(this, LoginActivity.class);
            startActivity(intent);
        });


        createUserBtn.setOnClickListener(view -> {


            progressBar.setVisibility(View.VISIBLE);
            String emailadd = email.getText().toString().trim();
            String passw = password.getText().toString().trim();
            Log.e("TAGEmail", "onCreate: " + emailadd);
            Log.e("TAGEmail", "onCreate: " + passw);

            if (!emailadd.isEmpty() && !passw.isEmpty()) {
                mAuth.createUserWithEmailAndPassword(emailadd, passw)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);

                                // Account creation success, handle the user accordingly
                                FirebaseUser user = mAuth.getCurrentUser();
                                String userId = user.getUid();
                                SharedPreferences.Editor editor = mpref.edit();
                                editor.putString("userId", userId);
                                editor.apply();

                                // ...

                                Intent intent = new Intent(this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                // Account creation failed, display an error message to the user
                                Toast.makeText(getApplicationContext(), "Account creation failed.", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                // Email or password is empty
                // Perform necessary actions or show an error message
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();

        String storedUserId = mpref.getString("userId", "");

        if (!storedUserId.isEmpty()) {
            // User ID exists in SharedPreferences and matches the current user
            // Go to the desired page
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

            // Optional: If you want to finish the current activity
        } else {
            // User ID exists in SharedPreferences but does not match the current user
            // Perform other necessary actions


        }
    }

}