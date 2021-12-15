package com.example.assignment2_android;

//import static com.example.assignment2_android.databaseFirestore.UserDatabase.getAllUsers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.Toast;

import com.example.assignment2_android.databaseFirestore.SiteLocationDatabase;
import com.example.assignment2_android.databaseFirestore.UserDatabase;
import com.example.assignment2_android.model.User;
import com.example.assignment2_android.model.VolunteerSite;
import com.example.assignment2_android.site.RandomLocation;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    // Declare button
    Button guide;
    Button explore;

    //Declare database
    FirebaseFirestore db;

    //Declare public List of string that can access later by other classes
    public static List<String> userEmails;

    //Declare public List of user and site that can access later by other classes
    public static List <User> allUsers;
    public static ArrayList<VolunteerSite>allSites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialize value for two arraylist ( avoid null exception)
        userEmails = new ArrayList<>();
        allUsers = new ArrayList<>();
        allSites = new ArrayList<>();
        //Initialize value for firestore
        db = FirebaseFirestore.getInstance();

        //Binding value to button
        guide = findViewById(R.id.guide);
        explore = findViewById(R.id.explore);

        //Switch to UserGuide activities
        guide.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, UserGuide.class);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(MainActivity.this, "Oops!! Something wrong, Please try again!", Toast.LENGTH_LONG).show();
            }
        });

        //Switch to LogIn activities
        explore.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LogIn.class);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(MainActivity.this, "Oops!! Something wrong, Please try again!", Toast.LENGTH_LONG).show();
            }
        });


        //Get all users that registered the app and store in the array allUsers
        UserDatabase.getAllUsers(db,allUsers);


//         Handle time delay between getting the data from database and store in the list
//         If the data is bigger or the internet is too slow, assign the higher delay time
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    // Check if that user is fetch properly
                    if (allUsers.size() != 0) {
                        // Loop the list and get the info of that person
                        for (int i = 0; i < allUsers.size(); i++) {
                            userEmails.add(allUsers.get(i).getEmail());
                        }
                    }
                },2000);

        //Get all sites that registered the app and store in the array allSites
        SiteLocationDatabase.fetchSiteLocations(db,allSites);

        // Handle time delay between getting the data from database and store in the list
        // If the data is bigger or the internet is too slow, assign the higher delay time
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Check if that sites is fetch properly
            if (allSites.size() != 0) {
                // Loop the list and get the info of that site
            }
        },1000);


    }
}