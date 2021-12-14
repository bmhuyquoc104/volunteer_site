package com.example.assignment2_android.listDisplay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.assignment2_android.R;
import com.example.assignment2_android.VolunteerHome;
import com.example.assignment2_android.adapter.VolunteerListAdapter;
import com.example.assignment2_android.databaseFirestore.UserDatabase;
import com.example.assignment2_android.model.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserList extends AppCompatActivity {
    private List<User> userList;
    private FirebaseFirestore db;
    private UserDatabase userDatabase;
    private ProgressDialog pd;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter volunteerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private VolunteerListAdapter adapter;
    View download;
    String reportDetail ="";
    String reportDir ="";
    String reportName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        userList = VolunteerHome.userByEmail;
        db = FirebaseFirestore.getInstance();
        userDatabase = new UserDatabase();
        pd = new ProgressDialog(this);
        // Set fixed size for recycler view
        recyclerView = findViewById(R.id.userList);
        recyclerView.setHasFixedSize(true);

        download = findViewById(R.id.downLoadListOfUsers);
        reportDir = "leaderDownloadList";
        reportName = "VolunteersList";
        //Use a linear layout manager
        layoutManager = new LinearLayoutManager(UserList.this);
        recyclerView.setLayoutManager(layoutManager);

        // Specify an adapter
        adapter = new VolunteerListAdapter(userList,this);
        recyclerView.setAdapter(adapter);

        //userDatabase.fetchVolunteers(this,db,pd,userList,adapter);
        if (!isExternalStorageAvailableForRW()) {
            download.setEnabled(false);
        }

        for (int i = 0; i < userList.size(); i++){
            String email = userList.get(i).getEmail();
            String username = userList.get(i).getName();
            int age = userList.get(i).getAge();
            reportDetail += i + ":" + " "+ "Email: " + email +"; " + "Username: " + username + "; " + "age: " + age + "."+ "\n";
        }


        download.setOnClickListener(view -> {
            // Check for Storage Permission
            if (isStoragePermissionGranted()) {
                // If input is not empty, we'll proceed
                if (!reportDetail.equals("")) {
                    // To access app-specific files from external storage, you can call
                    // getExternalFilesDir() method. It returns the path to
                    // storage > emulated > 0 > Android > data > [package_name] > files > MyFileDir
                    // or,
                    // storage > self > Android > data > [package_name] > files > MyFileDir
                    // directory on the SD card. Once the app is uninstalled files here also get
                    // deleted.
                    // Create a File object like this.
                    File file = new File(getExternalFilesDir(reportDir), reportName);
                    // Create an object of FileOutputStream for writing data to myFile.txt
                    FileOutputStream fileOutputStream = null;
                    try {
                        // Instantiate the FileOutputStream object and pass myExternalFile in constructor
                        fileOutputStream = new FileOutputStream(file);
                        // Write to the file
                        fileOutputStream.write(reportDetail.getBytes());
                        // Close the stream
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

    }

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