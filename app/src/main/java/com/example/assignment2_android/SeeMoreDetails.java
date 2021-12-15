package com.example.assignment2_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.assignment2_android.listDisplay.ParticipantList;

public class SeeMoreDetails extends AppCompatActivity {
    // Declare textview
    TextView  runLeader, runLat, runLng, runName, runCapacity, runVolunteers, runTestedNumber,
            runUserList, runType, runStatus;

    // Declare String
    String siteRole, siteLeader, siteLat, siteLng, siteName, siteCapacity, siteVolunteers, siteTestedNumber, siteStatus, siteType, siteListOfUsers;

    // Declare Button
    Button back;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_more_details);

        // Binding textview to its value
        runLat = (TextView) findViewById(R.id.runReportLat);
        runLeader = (TextView) findViewById(R.id.runLeader);
        runLng = findViewById(R.id.runLng);
        runName = findViewById(R.id.runName);
        runCapacity = findViewById(R.id.runMax);
        runVolunteers = findViewById(R.id.runVolunteers);
        runTestedNumber = findViewById(R.id.runTested);
        runType = findViewById(R.id.runType);
        runStatus = findViewById(R.id.runStatus);
        runUserList = findViewById(R.id.runUserList);
        back = findViewById(R.id.back);
        //Receive intents from previous activity
        Intent intent = getIntent();
        if (intent != null) {
            // Identify each intent by its key and then assign the value to the text view to display
            if (intent.hasExtra("siteRole")) {
                siteRole = intent.getStringExtra("siteRole");
            }

            if (intent.hasExtra("siteLeader")) {
                siteLeader = intent.getStringExtra("siteLeader");

                runLeader.setText("Leader: " + siteLeader);
            }

            if (intent.hasExtra("siteLat")) {
                siteLat = intent.getStringExtra("siteLat");

                runLat.setText("Latitude: " + siteLat);
            }

            if (intent.hasExtra("siteLng")) {
                siteLng = intent.getStringExtra("siteLng");
                runLng.setText("Longitude: " + siteLng);
            }

            if (intent.hasExtra("siteName")) {
                siteName = intent.getStringExtra("siteName");

                runName.setText("Site Name: " + siteName);
            }

            if (intent.hasExtra("siteCapacity")) {
                siteCapacity = intent.getStringExtra("siteCapacity");

                runCapacity.setText("Max Capacity: " + siteCapacity);
            }

            if (intent.hasExtra("siteVolunteers")) {
                siteVolunteers = intent.getStringExtra("siteVolunteers");

                runVolunteers.setText("Total Volunteers: " + siteVolunteers);
            }
            if (intent.hasExtra("siteTestedNumber")) {
                siteTestedNumber = intent.getStringExtra("siteTestedNumber");


                runTestedNumber.setText("Total Tested Numbers: " + siteTestedNumber);
            }
            if (intent.hasExtra("siteStatus")) {
                siteStatus = intent.getStringExtra("siteStatus");


                runStatus.setText("Status: " + siteStatus);
            }
            if (intent.hasExtra("siteType")) {
                siteType = intent.getStringExtra("siteType");

                runType.setText("Type: " + siteType);
            }
            if (intent.hasExtra("siteListOfUsers")) {
                siteListOfUsers = intent.getStringExtra("siteListOfUsers");
                if (siteRole.equals("leader")){
                    runUserList.setText("List Of User: " + siteListOfUsers);
                }
                else{
                    runUserList.setVisibility(View.INVISIBLE);
                }
            } else {
                String text = "Hello";
            }
        }

        back.setOnClickListener( view ->{
            Intent intent2 = new Intent(this, ParticipantList.class);
            // Delete all stacks before to avoid stack memory redundant and collapse between stacks
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent2);
        });
    }
}