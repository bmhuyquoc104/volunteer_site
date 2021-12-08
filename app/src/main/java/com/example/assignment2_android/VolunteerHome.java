package com.example.assignment2_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class VolunteerHome extends AppCompatActivity {
    View locationActivated ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_home);

        locationActivated = findViewById(R.id.locationActivation);
    }

    public void switchToLocationPage(View view) {
        Intent intent = new Intent(this, SiteLocation.class);
        startActivity(intent);
    }
}