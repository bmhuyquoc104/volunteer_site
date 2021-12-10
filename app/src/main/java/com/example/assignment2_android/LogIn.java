package com.example.assignment2_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment2_android.databaseFirestore.UserDatabase;
import com.example.assignment2_android.model.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class LogIn extends AppCompatActivity {
    //Declare variable username after logging
    String personalUsername;
    //Declare variable email after logging
    String personalEmail;
    //Declare variable id after logging
    String personalId;
    //Declare variable age after logging
    String personalAge;
    //Declare edittext for user to input username
    EditText username;
    //Declare edittext for user to input password
    EditText password;
    //Declare button for user to login
    Button login;
    TextView register, forgotPassword;
    //Declare list to store user info after logging
    List<User> oneUserlist;
    // Declare userDatabase class to use fetch method
    UserDatabase userDatabase;
    //Declare firebase
    FirebaseFirestore db;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //Binding username
        username = findViewById(R.id.userNamLogIn);
        //Binding password
        password = findViewById(R.id.passwordLogIn);
        //Binding login
        login = findViewById(R.id.signIn);
        //Initialize oneUserList
        oneUserlist = new ArrayList<>();
        //Initialize userDatabase
        userDatabase = new UserDatabase();
        //Initialize Firebase
        db = FirebaseFirestore.getInstance();
        pd = new ProgressDialog(this);


        //Login function
        login.setOnClickListener(view -> {

            //Store username input
            String inputUserName = username.getText().toString();
            //Store password input
            String inputUserPassword = password.getText().toString();
            //Call method to fetch user by id and password
            userDatabase.getUserByUserNameAndPassword(this, db, inputUserPassword, inputUserName, oneUserlist);
            //delay the system 2s to ensure that the list have enough time to store data in the list
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                // Check if that user is fetch properly
                if (oneUserlist.size() != 0) {
                    // Loop the list and get the info of that person
                    for (int i = 0; i < oneUserlist.size(); i++) {
                        personalUsername = oneUserlist.get(i).getName();
                        personalId = oneUserlist.get(i).getId();
                        personalAge = Integer.toString(oneUserlist.get(i).getAge());
                        personalEmail = oneUserlist.get(i).getEmail();
                    }
                    System.out.println("con me no" + personalUsername);
                    System.out.println("con me no" + personalEmail);

                    //Send this intend to userprofile activity for later using
                    Intent intent2 = new Intent(this, UserProfile.class);
                    intent2.setAction(Intent.ACTION_SEND);
                    intent2.setType("plain/text");
                    intent2.putExtra("userName", personalUsername);
                    intent2.putExtra("email", personalEmail);
                    intent2.putExtra("age", personalAge);
                    intent2.putExtra("id", personalId);
                    startActivity(intent2);

                    // Send this intent to volunteer home activity for later using
                    Intent intent = new Intent(this, VolunteerHome.class);
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("plain/text");
                    intent.putExtra("userName", personalUsername);
                    intent.putExtra("email", personalEmail);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    try {
                        // Start an activity
                        startActivity(intent);
                        Toast.makeText(LogIn.this, "You have successfully login!", Toast.LENGTH_LONG).show();

                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(LogIn.this, "Oops!! Something wrong, Please try again!", Toast.LENGTH_LONG).show();
                    }


                } else {
                    Toast.makeText(LogIn.this, "Wrong Username Or Password. Please try again!", Toast.LENGTH_LONG).show();
                }
            }, 2000);

        });


    }

    // Function to switch to Register activity
    public void switchToRegister(View view) {
        Intent intent = new Intent(this, Register.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(LogIn.this, "Oops!! Something wrong, Please try again!", Toast.LENGTH_LONG).show();
        }
    }


}