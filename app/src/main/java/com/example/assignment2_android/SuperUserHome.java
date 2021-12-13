package com.example.assignment2_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.assignment2_android.listDisplay.SiteList;

public class SuperUserHome extends AppCompatActivity {
    TextView welcomeAdmin;
    View seeAllSites;
    View logoutButton;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_user_home);
        welcomeAdmin = findViewById(R.id.adminWelcome);
        seeAllSites = findViewById(R.id.seeAllSites);
        logoutButton = findViewById(R.id.logout);


        Intent intent = getIntent();
        if (intent != null) {
            // Handle intent if the key = "text"
            if (intent.hasExtra("userName")) {
                String text = intent.getStringExtra("userName");
                // Set the name of textview to "edit"
                welcomeAdmin.setText("Welcome back " + text);
            } else {
                String text = "Hello";
                // Set the name of textview to "add"
                welcomeAdmin.setText(text);
            }
        }

        seeAllSites.setOnClickListener(view -> {
            Intent intent2 = new Intent(this, SiteList.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent2);
        });


        logoutButton.setOnClickListener(view -> {
            Intent intent2 = new Intent(this, LogIn.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent2);
        });

    }
}