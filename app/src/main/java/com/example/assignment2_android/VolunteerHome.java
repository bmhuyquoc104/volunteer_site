package com.example.assignment2_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.assignment2_android.listDisplay.ParticipantList;
import com.example.assignment2_android.listDisplay.UserList;

public class VolunteerHome extends AppCompatActivity {
    View locationActivated ;
    View displaySite;
    TextView welcome;
    View checkSite;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_home);
        displaySite = findViewById(R.id.ruleActivitation);
        locationActivated = findViewById(R.id.activateLocation);
        welcome = findViewById(R.id.userWelcome);
        checkSite = findViewById(R.id.checkSites);

        Intent intent = getIntent();
        if (intent != null) {
            // Handle intent if the key = "text"
            if (intent.hasExtra("userName")) {
                String text = intent.getStringExtra("userName");
                // Set the name of textview to "edit"
                welcome.setText("Welcome " + text);
            } else {
                String text = "Hello";
                // Set the name of textview to "add"
                welcome.setText(text);
            }
        }

        checkSite.setOnClickListener(view -> {
            Intent intent2 = new Intent(this, ParticipantList.class);
            startActivity(intent2);
        });
    }

    public void switchToLocationPage(View view) {
        Intent intent = new Intent(this, SiteLocation.class);
        startActivity(intent);
    }

    public void displaySite (View view){
        Intent intent = new Intent(this, UserList.class);
        startActivity(intent);
    }

    public void switchToProfilePage(View view){
        Intent intent = new Intent(this,UserProfile.class);
        startActivity(intent);
    }



}