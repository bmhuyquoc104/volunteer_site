package com.example.assignment2_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class RunReport extends AppCompatActivity {
    TextView runId, runLeader,runLat,runLng, runName,runCapacity,runVolunteers,runTestedNumber,runUserList,runType,runStatus;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_report);
        runId = (TextView) findViewById(R.id.idRun);
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


        Intent intent = getIntent();
        if (intent != null) {
            // Handle intent if the key = "text"
            if (intent.hasExtra("siteId")) {
                String siteId = intent.getStringExtra("siteId");

                // Set the name of textview to "edit"
                runId.setText("Site Id: " + siteId);
            }
            if (intent.hasExtra("siteLeader")) {
                String siteLeader = intent.getStringExtra("siteLeader");

                // Set the name of textview to "edit"
                runLeader.setText("Leader: " + siteLeader);
            }

            if (intent.hasExtra("siteLat")) {
                String siteLat = intent.getStringExtra("siteLat");

                // Set the name of textview to "edit"
                runLat.setText("Latitude: " + siteLat);
            }

            if (intent.hasExtra("siteLng")) {
                String siteLng = intent.getStringExtra("siteLng");
                System.out.println("hello" + siteLng);
                // Set the name of textview to "edit"
                runLng.setText("Longitude: " + siteLng);
            }

            if (intent.hasExtra("siteName")) {
                String siteName = intent.getStringExtra("siteName");

                // Set the name of textview to "edit"
                runName.setText("Site Name: " + siteName);
            }

            if (intent.hasExtra("siteCapacity")) {
                String siteCapacity = intent.getStringExtra("siteCapacity");

                // Set the name of textview to "edit"
                runCapacity.setText("Max Capacity: " + siteCapacity);
            }

            if (intent.hasExtra("siteVolunteers")) {
                String siteVolunteers = intent.getStringExtra("siteVolunteers");

                // Set the name of textview to "edit"
                runVolunteers.setText("Total Volunteers: " + siteVolunteers);
            }
            if (intent.hasExtra("siteTestedNumber")) {
                String siteTestedNumber = intent.getStringExtra("siteTestedNumber");


                // Set the name of textview to "edit"
                runTestedNumber.setText("Total Tested Numbers: " + siteTestedNumber);
            }
            if (intent.hasExtra("siteStatus")) {
                String siteStatus = intent.getStringExtra("siteStatus");


                // Set the name of textview to "edit"
                runStatus.setText("Status: " + siteStatus);
            }
            if (intent.hasExtra("siteType")) {
                String siteType = intent.getStringExtra("siteType");

                // Set the name of textview to "edit"
                runType.setText("Type: " + siteType);
            }
            if (intent.hasExtra("siteListOfUsers")) {
                String siteListOfUsers = intent.getStringExtra("siteListOfUsers");

                // Set the name of textview to "edit"
                runUserList.setText("List Of User: " + siteListOfUsers);
            }
            else {
                String text = "Hello";
            }
        }
    }
}