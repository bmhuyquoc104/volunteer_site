package com.example.assignment2_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.assignment2_android.leader.UserList;

public class VolunteerHome extends AppCompatActivity {
    View locationActivated ;
    View displaySite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_home);
        displaySite = findViewById(R.id.displaySite);
        locationActivated = findViewById(R.id.locationActivation);
    }

    public void switchToLocationPage(View view) {
        Intent intent = new Intent(this, SiteLocation.class);
        startActivity(intent);
    }

    public void displaySite (View view){
        Intent intent = new Intent(this, UserList.class);
        startActivity(intent);
    }
}