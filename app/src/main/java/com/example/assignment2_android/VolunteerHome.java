package com.example.assignment2_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment2_android.databaseFirestore.ParticipantDatabase;
import com.example.assignment2_android.databaseFirestore.UserDatabase;
import com.example.assignment2_android.listDisplay.ParticipantList;
import com.example.assignment2_android.listDisplay.UserList;
import com.example.assignment2_android.model.Participant;
import com.example.assignment2_android.model.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VolunteerHome extends AppCompatActivity {
    View locationActivated ;
    View displaySite;
    TextView welcome;
    View checkSite;
    View showVolunteer;
    View update;
    View logoutButton;
    View rule;
    List<User>currentUserList;
    public static List<User>userByEmail;
    FirebaseFirestore db;
    User currentUser = new User();
    List<Participant>participantList;
    String role = "";
    String userList = "";
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_home);
        locationActivated = findViewById(R.id.activateLocation);
        welcome = findViewById(R.id.userWelcome);
        checkSite = findViewById(R.id.checkSites);
        showVolunteer = findViewById(R.id.volunteersActivation);
        rule = findViewById(R.id.ruleActivation);
        logoutButton = findViewById(R.id.logout);
        currentUserList = LogIn.oneUserlist;
        participantList = new ArrayList<>();
        userByEmail = new ArrayList<>();
        update = findViewById(R.id.update);
        db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        if (intent != null) {
            // Handle intent if the key = "text"
            if (intent.hasExtra("userName")) {
                String text = intent.getStringExtra("userName");
                // Set the name of textview to "edit"
                welcome.setText("Welcome " + text);
            } else {
                String text = "Hello";
                // Set the name of textview to "add"
                welcome.setText(text);
            }
        }

        update.setVisibility(View.GONE);

        for (User thisUser: currentUserList){
            currentUser = new User(thisUser.getName(),thisUser.getPassword(),thisUser.getEmail(), thisUser.getAge(),thisUser.getId());
        }
        ParticipantDatabase.getRole(this,db,participantList,currentUser);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    // Check if that user is fetch properly
                    if (participantList.size() != 0) {
                        // Loop the list and get the info of that person
                        for (int i = 0; i < participantList.size(); i++) {
                            role = participantList.get(i).getRole();

                            if(role.equals("leader")){
                                userList = participantList.get(i).getUserList();
                                List<String> list = Arrays.asList(userList.split(","));
                                System.out.println(list);
                                for (String l:list
                                ) {
                                    System.out.println(l);
                                    UserDatabase.getUserByEmail(this,db,l,userByEmail);

                                }
                                showVolunteer.setEnabled(true);
                                update.setVisibility(View.VISIBLE);
                            }
                        }
                        System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee" + role);

                    }
                },1000);
        showVolunteer.setEnabled(false);

        checkSite.setOnClickListener(view -> {
            Intent intent2 = new Intent(this, ParticipantList.class);
            startActivity(intent2);
        });

        showVolunteer.setOnClickListener(view -> {
            Intent intent3 = new Intent(this,UserList.class);
            startActivity(intent3);
        });

        logoutButton.setOnClickListener(view ->{
            Intent intent4 = new Intent(this,LogIn.class);
            startActivity(intent4);
            Toast.makeText(this,"You have successfully log out",Toast.LENGTH_LONG).show();
        });
        rule.setOnClickListener(view ->{
            Intent intent5 = new Intent (this,UserGuide2.class);
            startActivity(intent5);
        });
    }

    public void switchToLocationPage(View view) {
        Intent intent = new Intent(this, SiteLocation.class);
        startActivity(intent);
    }

    public void displaySite (View view){
        Intent intent = new Intent(this, UserList.class);
        startActivity(intent);
    }

    public void switchToProfilePage(View view){
        Intent intent = new Intent(this,UserProfile.class);
        startActivity(intent);
    }



}