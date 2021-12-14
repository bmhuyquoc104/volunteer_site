package com.example.assignment2_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.assignment2_android.databaseFirestore.SiteLocationDatabase;
import com.example.assignment2_android.model.VolunteerSite;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateSiteBySuperUser extends AppCompatActivity {

    EditText runName, runCapacity, runVolunteers, runTestedNumber,
            runUserList, runType, runStatus;
    String siteId = "";
    String siteStatus = "";
    String siteType = "";
    String siteCapacity = "";
    String siteLeader = "";
    String siteLat = "";
    String siteLng = "";
    String siteTestedNumber = "";
    String siteDistance = "";
    String siteName = "";
    String siteListOfUsers = "";
    String siteVolunteers = "";
    String editStatus;
    String editCapacity;
    String editTestedNumber;
    String editSiteName;
    String editListOfUsers;
    String editSiteType;
    String editVolunteers;

    Button update;

    TextView runId, runLeader, runLat, runLng;
    FirebaseFirestore db;
    ProgressDialog pd;
    VolunteerSite volunteerSite;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_site_by_super_user);
        runId = findViewById(R.id.idEdit);
        runLat = findViewById(R.id.editLat);
        runLeader = findViewById(R.id.editLeader);
        runLng = findViewById(R.id.editLng);
        runName = findViewById(R.id.editName);
        runCapacity = findViewById(R.id.editMax);
        runVolunteers = findViewById(R.id.editVolunteers);
        runTestedNumber = findViewById(R.id.editTested);
        runType = findViewById(R.id.editType);
        runStatus = findViewById(R.id.editStatus);
        runUserList = findViewById(R.id.editUserList);
        update = findViewById(R.id.editButton);
        db = FirebaseFirestore.getInstance();
        pd = new ProgressDialog(this);
        volunteerSite = new VolunteerSite();
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("siteId")) {
                siteId = intent.getStringExtra("siteId");

                System.out.println("in id" + siteId);
                runId.setText("Site Id: " + siteId);
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
                System.out.println("hello ne" +siteType);
                runType.setText("Type: " + siteType);
            }

            if (intent.hasExtra("siteDistance")) {
                siteDistance = intent.getStringExtra("siteDistance");

                // Set the name of textview to "edit"
            }

            if (intent.hasExtra("siteListOfUsers")) {
                siteListOfUsers = intent.getStringExtra("siteListOfUsers");

                // Set the name of textview to "edit"
                runUserList.setText("List Of User: " + siteListOfUsers);
            } else {
                String text = "Hello";
            }
        }




        update.setOnClickListener(view -> {
            editCapacity = runCapacity.getText().toString();
            editStatus = runStatus.getText().toString();
            editSiteName = runName.getText().toString();
            editVolunteers = runVolunteers.getText().toString();
            editSiteType = runType.getText().toString();
            editListOfUsers = runUserList.getText().toString();
            editTestedNumber = runTestedNumber.getText().toString();
            volunteerSite = new VolunteerSite(siteId,
                    editSiteName, siteLeader, editStatus, Integer.parseInt(editCapacity),
                    Integer.parseInt(editVolunteers), editSiteType, Double.parseDouble(siteLat),
                    Double.parseDouble(siteLng), Double.parseDouble(siteDistance),
                    Integer.parseInt(editTestedNumber), editListOfUsers);
            System.out.println(volunteerSite);
            SiteLocationDatabase.updateSiteLocations(db, volunteerSite
                    , this);
        });
    }
}