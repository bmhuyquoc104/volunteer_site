package com.example.assignment2_android.databaseFirestore;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.assignment2_android.model.User;
import com.example.assignment2_android.model.VolunteerSite;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class SiteLocationDatabase {
    // Add new Site to Firestore
    public static void postSiteLocations(FirebaseFirestore db, ArrayList<VolunteerSite> volunteerSiteList, Context context, ProgressDialog progressDialog) {
        //Set the title of the dialog
        progressDialog.setTitle("Creating new site !");
        progressDialog.show();
        // Get and post site to firestore
        for (VolunteerSite volunteerSite :
                volunteerSiteList) {
            String id = volunteerSite.getLocationId();
            String locationName = volunteerSite.getLocationName();
            String leaderName = volunteerSite.getLeader();
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
            temp.put("locationName", locationName);
            temp.put("locationId", id);
            temp.put("leader", leaderName);
            temp.put("status", status);
            temp.put("userList", userList);
            temp.put("locationType", locationType);
            temp.put("maxCapacity", Integer.toString(maxCapacity));
            temp.put("totalVolunteers", Integer.toString(totalVolunteers));
            temp.put("lat", Double.toString(lat));
            temp.put("lng", Double.toString(lng));
            temp.put("distanceFromCurrentLocation", Double.toString(distanceFromCurrentLocation));
            temp.put("totalTestedVolunteers", Integer.toString(totalTestedVolunteers));

            db.collection("Sites").document(id).set(temp)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(context, "Data is updated successfully!", Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Please try again!!", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    // Update site to firestore
    public static void updateSiteLocations(FirebaseFirestore db, VolunteerSite site, Context context,ProgressDialog progressDialog) {
        // Get that site id and the re post it to database
        String id = site.getLocationId();
        progressDialog.setTitle("Updating site !");
        progressDialog.show();
        String locationType = site.getLocationType();
        String locationName = site.getLocationName();
        String maxCapacity = Integer.toString(site.getMaxCapacity());
        String status = site.getStatus();
        String totalVolunteers = Integer.toString(site.getTotalVolunteers());
        String totalTestedVolunteers = Integer.toString(site.getTotalTestedVolunteers());
        String distanceFromCurrentLocation = Double.toString(site.getDistanceFromCurrentLocation());
        String userList = site.getUserList();
        db.collection("Sites").document(id)
                .update("maxCapacity",maxCapacity,"totalTestedVolunteers",totalTestedVolunteers,"locationType",
                        locationType,"locationName",locationName,"status", status, "userList", userList,
                       "totalVolunteers", totalVolunteers, "distanceFromCurrentLocation", distanceFromCurrentLocation)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Site have been successfully updated", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "Error: " + task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // Get all sites from firestore and then store it in an array list
    public static void fetchSiteLocations(FirebaseFirestore db, ArrayList<VolunteerSite> volunteerSiteList) {
        db.collection("Sites").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        volunteerSiteList.clear();
                        for (DocumentSnapshot snapShot : task.getResult()) {
                            VolunteerSite volunteerSite = new VolunteerSite(
                                    snapShot.getString("locationId"),
                                    snapShot.getString("locationName"),
                                    snapShot.getString("leader"),
                                    snapShot.getString("status"),
                                    Integer.parseInt(Objects.requireNonNull(snapShot.getString("maxCapacity"))),
                                    Integer.parseInt(Objects.requireNonNull(snapShot.getString("totalVolunteers"))),
                                    snapShot.getString("locationType"),
                                    Double.parseDouble(Objects.requireNonNull(snapShot.getString("lat"))),
                                    Double.parseDouble(Objects.requireNonNull(snapShot.getString("lng"))),
                                    Double.parseDouble(Objects.requireNonNull(snapShot.getString("distanceFromCurrentLocation"))),
                                    Integer.parseInt(Objects.requireNonNull(snapShot.getString("totalTestedVolunteers"))),
                                    snapShot.getString("userList")
                            );
                            volunteerSiteList.add(volunteerSite);
                        }

                    }
                });
    }


}
