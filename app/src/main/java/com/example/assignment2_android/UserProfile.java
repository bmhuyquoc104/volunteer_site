package com.example.assignment2_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class UserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Intent intent = getIntent();
        if (intent != null) {
            // Handle intent if the key = "text"



            if (intent.hasExtra("userName")) {
                String userName = intent.getStringExtra("userName");
                // Set the name of textview to "edit"
                System.out.println("test1111111111" + userName);
//                welcome.setText("Welcome " + text);
            }
            if (intent.hasExtra("email")) {
                String email = intent.getStringExtra("email");
                // Set the name of textview to "edit"
                System.out.println("test1111111111" + email);
//                welcome.setText("Welcome " + text);
            }
            if (intent.hasExtra("age")) {
                String age = intent.getStringExtra("age");
                // Set the name of textview to "edit"
                System.out.println("test1111111111" + age);
//                welcome.setText("Welcome " + text);
            }
            if (intent.hasExtra("id")) {
                String id = intent.getStringExtra("id");
                // Set the name of textview to "edit"
                System.out.println("test1111111111" + id);
//                welcome.setText("Welcome " + text);
            }
            else {
                String text = "Hello";
                // Set the name of textview to "add"
                System.out.println(text);
            }
        }

    }



}