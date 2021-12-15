package com.example.assignment2_android;

//import static com.example.assignment2_android.databaseFirestore.UserDatabase.getAllUsers;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.assignment2_android.model.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class UserProfile extends AppCompatActivity {
    List<User> currentUser;
    EditText name,age,email;
    Button back;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        name = findViewById(R.id.nameProfile);
        age = findViewById(R.id.ageProfile);
        email = findViewById(R.id.emailProfile);
        currentUser = new ArrayList<>();
        back = findViewById(R.id.back);
        currentUser = LogIn.oneUserlist;
        for (User u:currentUser
             ) {
            name.setText("Username: " + u.getName());
            age.setText("Age: " +   Integer.toString(u.getAge()));
            email.setText("Email: " +u.getEmail());
        }
        back.setOnClickListener( view ->{
            Intent intent2 = new Intent(this,VolunteerHome.class);
            // Delete all stacks before to avoid stack memory redundant and collapse between stacks
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent2);
        });
    }

}