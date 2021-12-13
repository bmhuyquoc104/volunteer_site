package com.example.assignment2_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.Toast;

import com.example.assignment2_android.databaseFirestore.SiteLocationDatabase;
import com.example.assignment2_android.model.User;
import com.example.assignment2_android.model.VolunteerSite;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class UserGuide extends AppCompatActivity {
    MainActivity activity;
    Button back;
    public static ArrayList<VolunteerSite>allSites;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);
        allSites = new ArrayList<>();


        db = FirebaseFirestore.getInstance();
        SiteLocationDatabase.fetchSiteLocations(db,allSites);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Check if that user is fetch properly
            if (allSites.size() != 0) {
                // Loop the list and get the info of that person
            }
            System.out.println("lalalalalalalala" +allSites.size());
        },1000);
    }


}