package com.example.assignment2_android.databaseFirestore;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.assignment2_android.model.VolunteerSite;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class SiteLocationDatabase {
    public void postSiteLocations(FirebaseFirestore db, ArrayList<VolunteerSite> volunteerSiteList, Context context){
        for (VolunteerSite volunteerSite:
                volunteerSiteList) {
            String id = volunteerSite.getLocationId();
            String locationName = volunteerSite.getLocationName();
            String leaderName = volunteerSite.getLeaderName();
            String locationType = volunteerSite.getLocationType();
            String status = volunteerSite.getStatus();
            String userList = volunteerSite.getUserList();
            int maxCapacity = volunteerSite.getMaxCapacity();
            int totalVolunteers = volunteerSite.getTotalVolunteers();
            double lat = volunteerSite.getLat();
            double lng = volunteerSite.getLng();
            double distanceFromCurrentLocation = volunteerSite.getDistanceFromCurrentLocation();
            int totalTestedVolunteers = volunteerSite.getTotalTestedVolunteers();
            HashMap<String, Object> temp = new HashMap<>();
            temp.put("locationName",locationName);
            temp.put("leaderName",leaderName);
            temp.put("status", status);
            temp.put("userList", userList);
            temp.put("locationType", locationType);
            temp.put("maxCapacity", Integer.toString(maxCapacity));
            temp.put("totalVolunteers", Integer.toString(totalVolunteers));
            temp.put("lat", Double.toString(lat));
            temp.put("lng", Double.toString(lng));
            temp.put("distanceFromCurrentLocation", Double.toString(distanceFromCurrentLocation));
            temp.put("totalTestedVolunteers", Integer.toString(totalTestedVolunteers));
            db.collection("SiteLocations").document(id).set(temp)

                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(context, "Data is updated successfully!", Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Please try again!!", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }
}
