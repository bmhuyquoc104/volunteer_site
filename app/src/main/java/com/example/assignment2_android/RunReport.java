package com.example.assignment2_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class RunReport extends AppCompatActivity {
    //Declare textview
    TextView runId, runLeader, runLat, runLng, runName, runCapacity, runVolunteers, runTestedNumber,
            runUserList, runType, runStatus;
    //Declare String
    String siteId, siteLeader, siteLat, siteLng, siteName, siteCapacity, siteVolunteers, siteTestedNumber,
            siteStatus, siteType, siteListOfUsers;
    //Declare button
    Button download;
    Button back;
    //Declare String for download
    String reportDetail ="";
    String reportDir ="";
    String reportName = "";
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_report);
        //binding textview , button ,and edittext to its value
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
        download = findViewById(R.id.downloadReport);
        back = findViewById(R.id.back);

        //Assign directory to store the file after downloading
        reportDir = "superUserReport";
        reportName = "CovidVolunteerSiteReport";

        //Receive intents from previous activity
        Intent intent = getIntent();
        if (intent != null) {
            // Identify each intent by its key and then assign the value to the text view to display
            if (intent.hasExtra("siteId")) {
                siteId = intent.getStringExtra("siteId");

                runId.setText("Site Id: " + siteId);
            }
            if (intent.hasExtra("siteLeader")) {
                siteLeader = intent.getStringExtra("siteLeader");

                runLeader.setText("Leader: " + siteLeader);
            }

            if (intent.hasExtra("siteLat")) {
                siteLat = intent.getStringExtra("siteLat");

                // Set the name of textview to "edit"
                runLat.setText("Latitude: " + siteLat);
            }

            if (intent.hasExtra("siteLng")) {
                siteLng = intent.getStringExtra("siteLng");
                // Set the name of textview to "edit"
                runLng.setText("Longitude: " + siteLng);
            }

            if (intent.hasExtra("siteName")) {
                siteName = intent.getStringExtra("siteName");

                // Set the name of textview to "edit"
                runName.setText("Site Name: " + siteName);
            }

            if (intent.hasExtra("siteCapacity")) {
                siteCapacity = intent.getStringExtra("siteCapacity");

                // Set the name of textview to "edit"
                runCapacity.setText("Max Capacity: " + siteCapacity);
            }

            if (intent.hasExtra("siteVolunteers")) {
                siteVolunteers = intent.getStringExtra("siteVolunteers");

                // Set the name of textview to "edit"
                runVolunteers.setText("Total Volunteers: " + siteVolunteers);
            }
            if (intent.hasExtra("siteTestedNumber")) {
                siteTestedNumber = intent.getStringExtra("siteTestedNumber");


                // Set the name of textview to "edit"
                runTestedNumber.setText("Total Tested Numbers: " + siteTestedNumber);
            }
            if (intent.hasExtra("siteStatus")) {
                siteStatus = intent.getStringExtra("siteStatus");


                // Set the name of textview to "edit"
                runStatus.setText("Status: " + siteStatus);
            }
            if (intent.hasExtra("siteType")) {
                siteType = intent.getStringExtra("siteType");

                // Set the name of textview to "edit"
                runType.setText("Type: " + siteType);
            }
            if (intent.hasExtra("siteListOfUsers")) {
                siteListOfUsers = intent.getStringExtra("siteListOfUsers");

                // Set the name of textview to "edit"
                runUserList.setText("List Of User: " + siteListOfUsers);
            } else {
                String text = "Hello";
            }
        }

        // Check if storage is available or not
        if (!isExternalStorageAvailableForRW()) {
            download.setEnabled(false);
        }

        // Function download the file to external storage
        download.setOnClickListener(view -> {
            // Assign the detail for the report
            reportDetail = "Site Id: " + siteId + "\n" +
                          "Leader: " + siteLeader + "\n" +
                          "Site Name: " + siteName + "\n" +
                          "Latitude: " + siteLat + "\n" +
                          "Longitude: " + siteLng + "\n" +
                          "Max Capacity: " + siteCapacity + "\n" +
                          "Total volunteers: " + siteVolunteers + "\n" +
                          "Total tested volunteers: " + siteTestedNumber + "\n" +
                          "Status: " + siteStatus + "\n" +
                          "Type: " + siteType + "\n" +
                          "List Of Users: " + siteListOfUsers + "\n";
            if (isStoragePermissionGranted()) {
                if (!reportDetail.equals("")) {

                    File file = new File(getExternalFilesDir(reportDir), reportName);
                    // Create an object of FileOutputStream for writing data to myFile.txt
                    FileOutputStream fileOutputStream = null;
                    try {
                        fileOutputStream = new FileOutputStream(file);
                        fileOutputStream.write(reportDetail.getBytes());
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Clear the EditText
                    // Show a Toast message to inform the user that the operation has been successfully completed.
                    Toast.makeText(this, "Information saved to SD card.", Toast.LENGTH_SHORT).show();
                } else {
                    // If the Text field is empty show corresponding Toast message
                    Toast.makeText(this, "Text field can not be empty.", Toast.LENGTH_SHORT).show();
                }
            }

            if (!reportDetail.equals("")) {
                File file = new File(getExternalFilesDir(reportDir), reportName);
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(reportDetail.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, "Information saved to SD card.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Text field can not be empty.", Toast.LENGTH_SHORT).show();
            }


        });
        back.setOnClickListener( view ->{
            Intent intent2 = new Intent(this,SuperUserHome.class);
            // Delete all stacks before to avoid stack memory redundant and collapse between stacks
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent2);
        });
    }

    // Function check the storage
    private boolean isExternalStorageAvailableForRW() {
        // Check if the external storage is available for read and write by calling
        // Environment.getExternalStorageState() method. If the returned state is MEDIA_MOUNTED,
        // then you can read and write files. So, return true in that case, otherwise, false.
        String extStorageState = Environment.getExternalStorageState();
        if (extStorageState.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                //Permission is granted
                return true;
            } else {
                //Permission is revoked
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            //permission is automatically granted on sdk<23 upon installation
            //Permission is granted
            return true;
        }
    }
}