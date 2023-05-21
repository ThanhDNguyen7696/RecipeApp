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

public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    EditText email;
    EditText password;
    TextView gotoSignup;
    private SharedPreferences mpref;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.loginBtn);
        email = findViewById(R.id.emailaddress);
        gotoSignup = findViewById(R.id.gotoSignup);
        mpref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        progressBar = findViewById(R.id.progressBar);

        password = findViewById(R.id.psssword);
        FirebaseApp.initializeApp(this); // Initialize FirebaseApp

        mAuth = FirebaseAuth.getInstance();


        gotoSignup.setOnClickListener(view -> {
            Intent intent =new Intent(this, CreateActivity.class);
            startActivity(intent);
        });

        loginBtn.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);

            String emailadd = email.getText().toString().trim();
            String passw = password.getText().toString().trim();
            Log.e("TAGEmail", "onCreate: " + emailadd);
            Log.e("TAGEmail", "onCreate: " + passw);

            if (!emailadd.isEmpty() && !passw.isEmpty()) {
                mAuth.signInWithEmailAndPassword(emailadd, passw)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Account creation success, handle the user accordingly
                                FirebaseUser user = mAuth.getCurrentUser();
                                String userId = user.getUid();
                                SharedPreferences.Editor editor = mpref.edit();
                                editor.putString("userId", userId);
                                editor.apply();
                                Intent intent = new Intent(this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                // Account creation failed, display an error message to the user
                                Toast.makeText(getApplicationContext(), "Account creation failed.", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                progressBar.setVisibility(View.VISIBLE);

                // Email or password is empty
                // Perform necessary actions or show an error message
            }
        });


    }
}