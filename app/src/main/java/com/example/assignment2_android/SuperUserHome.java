package com.example.assignment2_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.assignment2_android.listDisplay.SiteList;

public class SuperUserHome extends AppCompatActivity {
    // Declare textview
    TextView welcomeAdmin;

    //Declare view
    View seeAllSites;
    View logoutButton;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_user_home);
        //binding textview and view to its value
        welcomeAdmin = findViewById(R.id.adminWelcome);
        seeAllSites = findViewById(R.id.seeAllSites);
        logoutButton = findViewById(R.id.logout);

        //Receive intents from previous activity
        Intent intent = getIntent();
        if (intent != null) {
            // Identify each intent by its key and then assign the value to the text view to display
            if (intent.hasExtra("userName")) {
                String text = intent.getStringExtra("userName");
                welcomeAdmin.setText("Welcome back " + text);
            } else {
                String text = "Hello";
                welcomeAdmin.setText(text);
            }
        }

        //Function activate see all sites
        seeAllSites.setOnClickListener(view -> {
            Intent intent2 = new Intent(this, SiteList.class);
            // Delete all stacks before to avoid stack memory redundant and collapse between stacks
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent2);
        });

        // Function logout
        logoutButton.setOnClickListener(view -> {
            Intent intent2 = new Intent(this, LogIn.class);
            // Delete all stacks before to avoid stack memory redundant and collapse between stacks
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent2);
        });

    }
}