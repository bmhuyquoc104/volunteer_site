package com.example.assignment2_android;

import static com.example.assignment2_android.databaseFirestore.UserDatabase.getAllUsers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.assignment2_android.model.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class UserProfile extends AppCompatActivity {
    List<User> currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        currentUser = new ArrayList<>();
        currentUser = LogIn.oneUserlist;
        System.out.println("taisaolainull2222222222" + currentUser);

    }

}