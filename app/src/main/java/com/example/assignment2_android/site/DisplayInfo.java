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

import com.example.assignment2_android.R;
import com.example.assignment2_android.SiteLocation;
import com.example.assignment2_android.model.User;
import com.example.assignment2_android.model.VolunteerSite;

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
    List<VolunteerSite> currentSite = new ArrayList<>();
    List<User>allUsers = new ArrayList<>();
    VolunteerSite volunteerSite = new VolunteerSite();



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
        //Set invisibility before jointing the site
//        locationName.setVisibility(View.INVISIBLE);
//        totalTestedVolunteers.setVisibility(View.INVISIBLE);
//        totalVolunteers.setVisibility(View.INVISIBLE);
//        maxCapacity.setVisibility(View.INVISIBLE);
//        lat.setVisibility(View.INVISIBLE);
//        lng.setVisibility(View.INVISIBLE);



        allSites = SiteLocation.volunteerSiteList;
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

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("locationName")) {
                String currentLocationName = intent.getStringExtra("locationName");
                for (int i = 0; i < allSites.size(); i++) {
                    if (currentLocationName.equals(allSites.get(i).getLocationName())) {
                        String siteLeaderName = allSites.get(i).getLeaderName();
                        String siteStatus = allSites.get(i).getStatus();
                        String siteDistance = Double.toString(allSites.get(i).getDistanceFromCurrentLocation());
                        String siteType = allSites.get(i).getLocationType();
                        leaderName.setText("Leader name: " + siteLeaderName);
                        status.setText("Status: " + siteStatus);
                        totalDistance.setText("Distance to your location: " + siteDistance + " km");
                        locationType.setText("Type: " + siteType);
                        currentSite.add(allSites.get(i));
                    }
                }
            }
        }
        System.out.println("truoc khi add" + currentSite);
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


        joinSite.setOnClickListener(view -> {
            for (VolunteerSite site : currentSite
            ) {
                site.setTotalVolunteers(site.getTotalVolunteers() + 1);
                site.setStatus(site.checkStatus(site.getMaxCapacity(),site.getTotalVolunteers()));
                site.setUserList(site.getUserList() + "," + user.getEmail());
                if (site.getTotalVolunteers() >= site.getMaxCapacity()){
                    joinSite.setVisibility(View.GONE);
                    announce.setVisibility(View.VISIBLE);
                    message.setVisibility(View.GONE);
                    checkbox.setVisibility(View.GONE);
                }


            }
        });

        System.out.println(allUsers);
        registerForFriend.setOnClickListener(view ->{
            String inputFriendEmail = editText.getText().toString();
            System.out.println("huyne!!!!!!!" + inputFriendEmail);
            for (int i = 0; i<allUsers.size();i++){
                System.out.println(allUsers.get(i).getEmail());
                if (inputFriendEmail.equals(allUsers.get(i).getEmail())){
                    System.out.println(allUsers.get(i));
                    for (VolunteerSite site:currentSite
                         ) {
                        site.setTotalVolunteers(site.getTotalVolunteers() + 1);
                        site.setStatus(site.checkStatus(site.getMaxCapacity(),site.getTotalVolunteers()));
                        site.setUserList(site.getUserList() + "," + allUsers.get(i).getEmail());
                        System.out.println("sau khi add" + currentSite);
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
                else{
                    System.out.println("deo tim thay");
                }

//                else{
//                    for (VolunteerSite site:currentSite
//                         ) {
//                        System.out.println(userEmail);
//                        site.setTotalVolunteers(site.getTotalVolunteers() + 1);
//                        site.setStatus(site.checkStatus(site.getMaxCapacity(),site.getTotalVolunteers()));
//                        site.setUserList(site.getUserList() + "," + userEmail.getEmail());
//                        if (site.getTotalVolunteers() >= site.getMaxCapacity()){
//                            joinSite.setVisibility(View.GONE);
//                            announce.setVisibility(View.VISIBLE);
//                            message.setVisibility(View.GONE);
//                            checkbox.setVisibility(View.GONE);
//                        }
//                    }
//                }
            }
        });
    }

}