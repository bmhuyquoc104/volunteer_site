package com.example.assignment2_android;

//import static com.example.assignment2_android.databaseFirestore.UserDatabase.getAllUsers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.assignment2_android.model.User;
import com.example.assignment2_android.model.VolunteerSite;
import com.example.assignment2_android.site.RandomLocation;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button guide;
    Button explore;
    VolunteerSite volunteerSite = new VolunteerSite();
    RandomLocation randomLocation = new RandomLocation();
    ArrayList<VolunteerSite> volunteerSiteList = new ArrayList<>();
    public static ArrayList<User> allUsers = new ArrayList<>();
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<User> tempAllUsers = new ArrayList<>();
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
        db = FirebaseFirestore.getInstance();
//        getAllUsers(db,allUsers);

    }
}