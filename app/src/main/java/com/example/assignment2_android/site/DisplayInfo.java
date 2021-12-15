package com.example.assignment2_android.site;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment2_android.LogIn;
import com.example.assignment2_android.MainActivity;
import com.example.assignment2_android.R;
import com.example.assignment2_android.SiteLocation;
import com.example.assignment2_android.VolunteerHome;
import com.example.assignment2_android.databaseFirestore.ParticipantDatabase;
import com.example.assignment2_android.databaseFirestore.SiteLocationDatabase;
import com.example.assignment2_android.model.Participant;
import com.example.assignment2_android.model.User;
import com.example.assignment2_android.model.VolunteerSite;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DisplayInfo extends AppCompatActivity {
    //Declare textview
    TextView leaderName, status, locationType, announce, totalDistance, message;
    List<VolunteerSite> allSites = new ArrayList<>();
    CheckBox checkbox;
    EditText editText;
    String role = "";
    List<Participant> participantList;
    //Declare button
    Button joinSite, registerForFriend;

    //Declare and initialize list and class
    List<VolunteerSite> currentSiteList = new ArrayList<>();
    VolunteerSite currentSite = new VolunteerSite();
    List<User> totalUsers;
    List<User> currentUserList = new ArrayList<>();
    List<String> emails;
    User currentUser = new User();
    Participant participant = new Participant();
    FirebaseFirestore db;
    ProgressDialog pd;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_info);

        //Binding textview and button
        leaderName = findViewById(R.id.leaderName);
        status = findViewById(R.id.status);
        locationType = findViewById(R.id.type);
        totalDistance = findViewById(R.id.siteDistance);
        emails = MainActivity.userEmails;
        message = findViewById(R.id.message);
        joinSite = findViewById(R.id.joinSite);
        totalUsers = MainActivity.allUsers;
        announce = findViewById(R.id.announce);
        registerForFriend = findViewById(R.id.registerForFriend);
        checkbox = findViewById(R.id.registerSelection);
        editText = findViewById(R.id.friendEmail);
        db = FirebaseFirestore.getInstance();
        pd = new ProgressDialog(this);
        //Initializing
        currentUserList = LogIn.oneUserlist;
        participantList = new ArrayList<>();

        //Set invisibility before joining the site
        registerForFriend.setVisibility(View.GONE);

        joinSite.setVisibility(View.GONE);
        announce.setVisibility(View.GONE);

        editText.setVisibility(View.GONE);


        allSites = SiteLocation.volunteerSiteList;


        //Get intent from siteLocation and use that intent to render info
        Intent intent = getIntent();
        if (intent != null) {
            //Check key of intent and get value
            if (intent.hasExtra("locationName")) {
                String currentLocationName = intent.getStringExtra("locationName");
                //Find current site by locationName
                for (int i = 0; i < allSites.size(); i++) {
                    if (currentLocationName.equals(allSites.get(i).getLocationName())) {
                        //Render site info
                        String siteLeaderName = allSites.get(i).getLeader();
                        String siteStatus = allSites.get(i).getStatus();
                        String siteDistance = Double.toString(allSites.get(i).getDistanceFromCurrentLocation());
                        String siteType = allSites.get(i).getLocationType();
                        leaderName.setText("Leader name: " + siteLeaderName);
                        status.setText("Status: " + siteStatus);
                        totalDistance.setText("Distance to your location: " + siteDistance + " km");
                        locationType.setText("Type: " + siteType);
                        currentSiteList.add(allSites.get(i));
                    }
                }
            }
        }


        //Get the current user value
        for (User thisUser : currentUserList
        ) {
            currentUser = new User(thisUser.getName(), thisUser.getPassword(), thisUser.getEmail(), thisUser.getAge(), thisUser.getId());
        }

        //Get current site
        for (VolunteerSite thisSite : currentSiteList
        ) {
            currentSite = new VolunteerSite(thisSite.getLocationId(), thisSite.getLocationName(),
                    thisSite.getLeader(), thisSite.getStatus(), thisSite.getMaxCapacity(),
                    thisSite.getTotalVolunteers(), thisSite.getLocationType(), thisSite.getLat(),
                    thisSite.getLng(), thisSite.getDistanceFromCurrentLocation(),
                    thisSite.getTotalTestedVolunteers(), thisSite.getUserList());
        }


//        ParticipantDatabase.getRole(this,db,participantList,currentUser);

        //Check whether the site is full or not to display or conceal button and proper message
        for (VolunteerSite site : currentSiteList
        ) {
            System.out.println(site.getStatus());
            if (!site.getStatus().equals("full")) {
                joinSite.setVisibility(View.VISIBLE);
            } else {
                announce.setVisibility(View.VISIBLE);
                message.setVisibility(View.GONE);
                checkbox.setVisibility(View.GONE);
            }
        }

        //Check if you are in the stie or not, if yes, can only register for friend
        if (currentSite.getUserList().contains(currentUser.getEmail())) {
            if (!currentSite.getStatus().equals("full")) {
                joinSite.setVisibility(View.GONE);
                joinSite.setEnabled(false);
                announce.setVisibility(View.GONE);
                editText.setVisibility(View.GONE);
                announce.setVisibility(View.GONE);
                message.setVisibility(View.GONE);
            }else{
                registerForFriend.setVisibility(View.GONE);
                joinSite.setVisibility(View.GONE);
                announce.setVisibility(View.GONE);
                editText.setVisibility(View.GONE);
                announce.setVisibility(View.VISIBLE);
            }
        }

        // Check to display site info base on leader role
        if(currentSite.getLeader().equals(currentUser.getEmail())){
            // Check if site is full or not to display info
            if (!currentSite.getStatus().equals("full")) {
                System.out.println(currentSite.getStatus());
                joinSite.setVisibility(View.GONE);
                joinSite.setEnabled(false);
                announce.setVisibility(View.GONE);
                checkbox.setVisibility(View.VISIBLE);
                editText.setVisibility(View.GONE);
                announce.setVisibility(View.GONE);
                message.setVisibility(View.GONE);
            }else{
                registerForFriend.setVisibility(View.GONE);
                joinSite.setVisibility(View.GONE);
                announce.setVisibility(View.GONE);
                editText.setVisibility(View.GONE);
                checkbox.setVisibility(View.GONE);
            }
        }

            //Check checkbox to display either register yourself or friend
            checkbox.setOnClickListener(view -> {
                if (checkbox.isChecked()) {
                    editText.setVisibility(View.VISIBLE);
                    registerForFriend.setVisibility(View.VISIBLE);
                    joinSite.setVisibility(View.GONE);
                } else {
                    editText.setVisibility(View.GONE);
                    registerForFriend.setVisibility(View.GONE);
                    joinSite.setVisibility(View.VISIBLE);
                }
            });

            //Register the user to the site
            joinSite.setOnClickListener(view -> {
                for (VolunteerSite site : currentSiteList
                ) {
                    site.setTotalVolunteers(site.getTotalVolunteers() + 1);
                    site.setStatus(site.checkStatus(site.getMaxCapacity(), site.getTotalVolunteers()));
                    site.setUserList(site.getUserList() + "," + currentUser.getEmail());
                    SiteLocationDatabase.updateSiteLocations(db, site, this,pd);
                    //Update site after user joining

                    //Create participant for current user
                    participant = new Participant(currentUser, "volunteer", site);
                    //Add participant to participant database
                    ParticipantDatabase.addParticipant(participant, db, this);

                    Intent intent3 = new Intent(this, VolunteerHome.class);
                    intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent3);

                    //If the site is full, re render the page
                    Toast.makeText(this, "You have successfully registered yourself to this site", Toast.LENGTH_LONG).show();
                    joinSite.setVisibility(View.GONE);
                    checkbox.setVisibility(View.GONE);
                    if (site.getTotalVolunteers() >= site.getMaxCapacity()) {
                        joinSite.setVisibility(View.GONE);
                        announce.setVisibility(View.VISIBLE);
                        message.setVisibility(View.GONE);
                        checkbox.setVisibility(View.GONE);
                    }


                }
            });

            //Add friend's user to site
            registerForFriend.setOnClickListener(view -> {
                String inputFriendEmail = editText.getText().toString();
                //Use the email to check if the account is existed ?
//            for (int i = 0; i<totalUsers.size();i++){
//                //Get that friend's account
//                if (inputFriendEmail.equals(totalUsers.get(i).getEmail())){
                //Get  current site
                for (VolunteerSite updateSite : currentSiteList
                ) {
                    updateSite.setTotalVolunteers(updateSite.getTotalVolunteers() + 1);
                    updateSite.setStatus(updateSite.checkStatus(updateSite.getMaxCapacity(), updateSite.getTotalVolunteers()));
                    updateSite.setUserList(updateSite.getUserList() + "," + inputFriendEmail);
                    SiteLocationDatabase.updateSiteLocations(db, updateSite, this,pd);

                    //Update new site after user's friend joining
//                        participant = new Participant(totalUsers.get(i),"volunteer",site);

                    //Post data to participant database
                    ParticipantDatabase.addParticipantByEmail(inputFriendEmail, db, this, updateSite, "volunteer");

                    //Create participant for friend's user
                    //If the site is full, re render the page
                    Toast.makeText(this, "You have successfully registered your friend to this site", Toast.LENGTH_LONG).show();
                    registerForFriend.setVisibility(View.GONE);
                    checkbox.setVisibility(View.GONE);
                    editText.setVisibility(View.GONE);
                    if (updateSite.getTotalVolunteers() >= updateSite.getMaxCapacity()) {
                        joinSite.setVisibility(View.GONE);
                        announce.setVisibility(View.VISIBLE);
                        message.setVisibility(View.GONE);
                        checkbox.setVisibility(View.GONE);
                        registerForFriend.setVisibility(View.GONE);
                        editText.setVisibility(View.GONE);
                    }
                }
//                }
//                //Prompt user if they input wrong their friend's email
//                else{
//                    Toast.makeText(this,"Input email is not existed, please register an account before joning the site",Toast.LENGTH_LONG).show();
//                }

                // }
            });
        }

    }