package com.example.assignment2_android.site;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.example.assignment2_android.R;
import com.example.assignment2_android.SiteLocation;
import com.example.assignment2_android.databaseFirestore.ParticipantDatabase;
import com.example.assignment2_android.databaseFirestore.SiteLocationDatabase;
import com.example.assignment2_android.model.Participant;
import com.example.assignment2_android.model.User;
import com.example.assignment2_android.model.VolunteerSite;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class DisplayInfo extends AppCompatActivity {
    //Declare textview
    TextView leaderName, status, locationName, locationType, announce,totalDistance, totalTestedVolunteers, totalVolunteers, maxCapacity, lat, lng, message;
    List<VolunteerSite> allSites = new ArrayList<>();
    CheckBox checkbox;
    EditText editText;

    //Declare button
    Button joinSite ,registerForFriend;

    //Declare and initialize list and class
    List<VolunteerSite> currentSite = new ArrayList<>();
    List<User>allUsers = new ArrayList<>();
    VolunteerSite volunteerSite = new VolunteerSite();
    List<User>currentUserList = new ArrayList<>();
    User currentUser = new User();
    Participant participant = new Participant();
    FirebaseFirestore db;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_info);

        //Binding textview and button
        leaderName = findViewById(R.id.leaderName);
        status = findViewById(R.id.status);
        locationName = findViewById(R.id.siteName);
        locationType = findViewById(R.id.type);
        totalDistance = findViewById(R.id.siteDistance);
        totalTestedVolunteers = findViewById(R.id.siteTotalTested);
        totalVolunteers = findViewById(R.id.siteTotalVolunteers);
        maxCapacity = findViewById(R.id.siteMaxCapacity);
        lat = findViewById(R.id.siteLat);
        lng = findViewById(R.id.siteLng);
        message = findViewById(R.id.message);
        joinSite = findViewById(R.id.joinSite);
        announce = findViewById(R.id.announce);
        registerForFriend = findViewById(R.id.registerForFriend);
        checkbox = findViewById(R.id.registerSelection);
        editText = findViewById(R.id.friendEmail);
        db = FirebaseFirestore.getInstance();

        //Initializing
        currentUserList = LogIn.oneUserlist;

        //Set invisibility before jointing the site
        registerForFriend.setVisibility(View.GONE);
        lng.setVisibility(View.GONE);
        lat.setVisibility(View.GONE);
        maxCapacity.setVisibility(View.GONE);
        totalVolunteers.setVisibility(View.GONE);
        totalTestedVolunteers.setVisibility(View.GONE);
        locationName.setVisibility(View.GONE);
        joinSite.setVisibility(View.GONE);
        announce.setVisibility(View.GONE);
        editText.setVisibility(View.GONE);




        allSites = SiteLocation.volunteerSiteList;


        //Dummy data
        User user = new User("huy", "1231", "huyvipro12121@gmail.com", 12, "1231");
        User user5 = new User("huy", "123", "huy@gmail.com", 123, "31231");
        User user1 = new User("huy", "123", "huy104@gmail.com", 123, "31231");
        User user2 = new User("huy", "123", "huy123@gmail.com", 123, "31231");
        User user3 = new User("huy", "123", "huy134@gmail.com", 123, "31231");
        User user4 = new User("huy", "123", "huy156@gmail.com", 123, "31231");
        allUsers.add(user);
        allUsers.add(user1);
        allUsers.add(user2);
        allUsers.add(user3);
        allUsers.add(user4);
        allUsers.add(user5);

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
                        String siteLeaderName = allSites.get(i).getLeaderName();
                        String siteStatus = allSites.get(i).getStatus();
                        String siteDistance = Double.toString(allSites.get(i).getDistanceFromCurrentLocation());
                        String siteType = allSites.get(i).getLocationType();
                        String siteTotalVolunteers = Integer.toString(allSites.get(i).getTotalVolunteers());
                        String siteTotalTested = Integer.toString(allSites.get(i).getTotalTestedVolunteers());
                        String siteLat = Double.toString(allSites.get(i).getLat());
                        String siteLng = Double.toString(allSites.get(i).getLng());
                        String siteName = allSites.get(i).getLocationName();
                        String siteMaxCapacity = Integer.toString(allSites.get(i).getMaxCapacity());
                        leaderName.setText("Leader name: " + siteLeaderName);
                        status.setText("Status: " + siteStatus);
                        totalDistance.setText("Distance to your location: " + siteDistance + " km");
                        locationType.setText("Type: " + siteType);
                        locationName.setText("Location Name: " + siteName);
                        lat.setText("Lat: " + siteLat);
                        lng.setText("Lng: " + siteLng);
                        totalVolunteers.setText("Total Volunteers: " + siteTotalVolunteers);
                        totalTestedVolunteers.setText("Total Tested Volunteers: " + siteTotalTested);
                        maxCapacity.setText("Max Capacity: " + siteMaxCapacity);
                        currentSite.add(allSites.get(i));
                    }
                }
            }
        }

        //Check whether the site is full or not to display or conceal button and proper message
        for (VolunteerSite site : currentSite
        ) {
            System.out.println(site.getStatus());
            if (!site.getStatus().equals("full")) {
                joinSite.setVisibility(View.VISIBLE);
            }
            else{
                announce.setVisibility(View.VISIBLE);
                message.setVisibility(View.GONE);
                checkbox.setVisibility(View.GONE);
            }
        }

        //Check checkbox to display either register yourself or friend
        checkbox.setOnClickListener(view ->{
            if (checkbox.isChecked()){
                editText.setVisibility(View.VISIBLE);
                registerForFriend.setVisibility(View.VISIBLE);
                joinSite.setVisibility(View.GONE);
            }
            else{
                editText.setVisibility(View.GONE);
                registerForFriend.setVisibility(View.GONE);
                joinSite.setVisibility(View.VISIBLE);
            }
        });


        //Get the current user value
        for (User thisUser:currentUserList
             ) {
            currentUser = new User(thisUser.getName(),thisUser.getPassword(),thisUser.getEmail(), thisUser.getAge(),thisUser.getId());
        }


        //Add the user to the site
        joinSite.setOnClickListener(view -> {
            for (VolunteerSite site : currentSite
            ) {
                site.setTotalVolunteers(site.getTotalVolunteers() + 1);
                site.setStatus(site.checkStatus(site.getMaxCapacity(),site.getTotalVolunteers()));
                site.setUserList(site.getUserList() + "," + currentUser.getEmail());
                SiteLocationDatabase.updateSiteLocations(db,site,this);
                //Update site after user joining

                //Create participant for current user
                participant = new Participant(currentUser,"volunteer",site);
                //Add participant to participant database
                ParticipantDatabase.addParticipant(participant,db,this);

                //If the site is full, re render the page
                Toast.makeText(this,"You have successfully registered yourself to this site",Toast.LENGTH_LONG).show();
                lng.setVisibility(View.VISIBLE);
                lat.setVisibility(View.VISIBLE);
                maxCapacity.setVisibility(View.VISIBLE);
                totalVolunteers.setVisibility(View.VISIBLE);
                totalTestedVolunteers.setVisibility(View.VISIBLE);
                locationName.setVisibility(View.VISIBLE);
                joinSite.setVisibility(View.GONE);
                checkbox.setVisibility(View.GONE);
                if (site.getTotalVolunteers() >= site.getMaxCapacity()){
                    joinSite.setVisibility(View.GONE);
                    announce.setVisibility(View.VISIBLE);
                    message.setVisibility(View.GONE);
                    checkbox.setVisibility(View.GONE);
                }


            }
        });

        //Add friend's user to site
        registerForFriend.setOnClickListener(view ->{
            String inputFriendEmail = editText.getText().toString();
            //Use the email to check if the account is existed ?
            for (int i = 0; i<allUsers.size();i++){
                //Get that friend's account
                if (inputFriendEmail.equals(allUsers.get(i).getEmail())){
                    //Get  current site
                    for (VolunteerSite site:currentSite
                         ) {

                        site.setTotalVolunteers(site.getTotalVolunteers() + 1);
                        site.setStatus(site.checkStatus(site.getMaxCapacity(),site.getTotalVolunteers()));
                        site.setUserList(site.getUserList() + "," + allUsers.get(i).getEmail());
                        SiteLocationDatabase.updateSiteLocations(db,site,this);



                        //Update new site after user's friend joining
                        participant = new Participant(allUsers.get(i),"volunteer",site);

                        //Post data to participant database
                        ParticipantDatabase.addParticipant(participant,db,this);

                        //Create participant for friend's user
                        //If the site is full, re render the page
                        Toast.makeText(this,"You have successfully registered your friend to this site",Toast.LENGTH_LONG).show();
                        lng.setVisibility(View.VISIBLE);
                        lat.setVisibility(View.VISIBLE);
                        maxCapacity.setVisibility(View.VISIBLE);
                        totalVolunteers.setVisibility(View.VISIBLE);
                        totalTestedVolunteers.setVisibility(View.VISIBLE);
                        locationName.setVisibility(View.VISIBLE);
                        registerForFriend.setVisibility(View.GONE);
                        checkbox.setVisibility(View.GONE);
                        editText.setVisibility(View.GONE);
                        if (site.getTotalVolunteers() >= site.getMaxCapacity()){
                            joinSite.setVisibility(View.GONE);
                            announce.setVisibility(View.VISIBLE);
                            message.setVisibility(View.GONE);
                            checkbox.setVisibility(View.GONE);
                            registerForFriend.setVisibility(View.GONE);
                            editText.setVisibility(View.GONE);
                        }
                    }
                    break;
                }
                //Prompt user if they input wrong their friend's email
                else{
                    Toast.makeText(this,"Input email is not existed, please register an account before joning the site",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

}