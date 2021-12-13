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
    Button guide;
    Button explore;
    FirebaseFirestore db;
    public static List<String> userEmails;

    public static List <User> allUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userEmails = new ArrayList<>();
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


//        UserDatabase.getAllUsers(db,allUsers);
//        new Handler(Looper.getMainLooper()).postDelayed(() -> {
//                    // Check if that user is fetch properly
//                    if (allUsers.size() != 0) {
//                        // Loop the list and get the info of that person
//                        for (int i = 0; i < allUsers.size(); i++) {
//                            userEmails.add(allUsers.get(i).getEmail());
//                        }
//                        System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee" + userEmails +userEmails.size());
//                    }
//                },4000);

    }

}