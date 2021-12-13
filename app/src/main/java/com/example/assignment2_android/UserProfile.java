package com.example.assignment2_android;

//import static com.example.assignment2_android.databaseFirestore.UserDatabase.getAllUsers;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;

import com.example.assignment2_android.model.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class UserProfile extends AppCompatActivity {
    List<User> currentUser;
    EditText name,age,email;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        name = findViewById(R.id.nameProfile);
        age = findViewById(R.id.ageProfile);
        email = findViewById(R.id.emailProfile);
        currentUser = new ArrayList<>();
        currentUser = LogIn.oneUserlist;
        for (User u:currentUser
             ) {
            System.out.println(u);
            name.setText("Username: " + u.getName());
            age.setText("Age: " +   Integer.toString(u.getAge()));
            email.setText("Email: " +u.getEmail());
        }

    }

}