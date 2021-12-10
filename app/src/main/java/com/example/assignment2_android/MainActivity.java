package com.example.assignment2_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.assignment2_android.model.VolunteerSite;
import com.example.assignment2_android.site.RandomLocation;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button guide;
    Button explore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        guide = findViewById(R.id.guide);
        explore = findViewById(R.id.explore);
        guide.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, UserGuide.class);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(MainActivity.this, "Oops!! Something wrong, Please try again!", Toast.LENGTH_LONG).show();
            }
        });

        explore.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LogIn.class);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(MainActivity.this, "Oops!! Something wrong, Please try again!", Toast.LENGTH_LONG).show();
            }
        });

        VolunteerSite volunteerSite = new VolunteerSite();
        LatLng HOCHIMINH = new LatLng(10.762622, 106.660172);
        List<Integer> randomMaxCapacity = new ArrayList<>();
        List<Integer> randomTotalNumber = new ArrayList<>();
        List<Integer> randomTestedVolunteer = new ArrayList<>();
        List<String> randomType = new ArrayList<>();
        List<String> randomLeaderName = new ArrayList<>();
        ArrayList<VolunteerSite> volunteerSiteList = new ArrayList<>();
        RandomLocation randomLocation = new RandomLocation();
        volunteerSite.generateNewLocation(HOCHIMINH, 4, 3000, volunteerSiteList, randomLocation);
    }
}