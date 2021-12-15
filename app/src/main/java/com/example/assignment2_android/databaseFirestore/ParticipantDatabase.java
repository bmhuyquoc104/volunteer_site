package com.example.assignment2_android.databaseFirestore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.assignment2_android.adapter.ParticipantListAdapter;
import com.example.assignment2_android.model.Participant;
import com.example.assignment2_android.model.User;
import com.example.assignment2_android.model.VolunteerSite;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class ParticipantDatabase {
    // Add new participant to firestore
    public static void addParticipant(Participant participant, FirebaseFirestore db,
                                      Context context) {

        String id = UUID.randomUUID().toString();
        String role = participant.getRole();

        String listOfUsers = participant.getVolunteerSite().getUserList();


        String email = participant.getUser().getEmail();
        String password = participant.getUser().getPassword();
        String userId = participant.getUser().getId();
        String name = participant.getUser().getName();
        int age = participant.getUser().getAge();


        String locationId = participant.getVolunteerSite().getLocationId();
        String locationName = participant.getVolunteerSite().getLocationName();
        String leader = participant.getVolunteerSite().getLeader();
        String locationType = participant.getVolunteerSite().getLocationType();
        String status = participant.getVolunteerSite().getStatus();
        String maxCapacity = Integer.toString(participant.getVolunteerSite().getMaxCapacity());
        String totalVolunteers = Integer.toString(participant.getVolunteerSite().getTotalVolunteers());
        String lat = Double.toString(participant.getVolunteerSite().getLat());
        String lng = Double.toString(participant.getVolunteerSite().getLng());
        String distanceFromCurrentLocation = Double.toString(participant.getVolunteerSite().getDistanceFromCurrentLocation());
        String totalTestedVolunteers = Integer.toString(participant.getVolunteerSite().getTotalTestedVolunteers());


        HashMap<String, Object> temp = new HashMap<>();
            temp.put("locationName", locationName);
            temp.put("distanceFromCurrentLocation", distanceFromCurrentLocation);
            temp.put("status", status);
            temp.put("locationType", locationType);
            temp.put("role", role);
            temp.put("email", email);
            temp.put("userList", listOfUsers);
            temp.put("ParticipantId", id);
            temp.put("lat",lat);
            temp.put("maxCapacity",maxCapacity);
            temp.put("leader",leader);
            temp.put("totalVolunteers",totalVolunteers);
            temp.put("lng",lng);
            temp.put("totalTestedVolunteers",totalTestedVolunteers);



        db.collection("Participant").document(id).set(temp)

                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
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


    // add by email (register for friend and your self in display info)
    public static void addParticipantByEmail(String email, FirebaseFirestore db,
                                      Context context,VolunteerSite site,String role) {
        String id = UUID.randomUUID().toString();
        String locationName = site.getLocationName();
        String locationType = site.getLocationType();
        String leader = site.getLeader();
        String status = site.getStatus();
        String userList = site.getUserList();
        String distanceFromCurrentLocation = Double.toString(site.getDistanceFromCurrentLocation());
        String maxCapacity = Integer.toString(site.getMaxCapacity());
        String totalVolunteers = Integer.toString(site.getTotalVolunteers());
        String lat = Double.toString(site.getLat());
        String lng = Double.toString(site.getLng());
        String totalTestedVolunteers = Integer.toString(site.getTotalTestedVolunteers());



        HashMap<String, Object> temp = new HashMap<>();
        temp.put("locationName", locationName);
        temp.put("distanceFromCurrentLocation", distanceFromCurrentLocation);
        temp.put("status", status);
        temp.put("locationType", locationType);
        temp.put("role", role);
        temp.put("email", email);
        temp.put("userList", userList);
        temp.put("ParticipantId", id);
        temp.put("lat",lat);
        temp.put("maxCapacity",maxCapacity);
        temp.put("leader",leader);
        temp.put("totalVolunteers",totalVolunteers);
        temp.put("lng",lng);
        temp.put("totalTestedVolunteers",totalTestedVolunteers);

        db.collection("Participant").document(id).set(temp)

                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
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


    // Get all participants and store in the list
    public static void fetchParticipant(Context context, FirebaseFirestore db, List<Participant> list, User user, ParticipantListAdapter adapter) {
        String email = user.getEmail();
        CollectionReference participantRef = db.collection("Participant");
        participantRef.whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot snapShot : task.getResult()) {
                                Participant participant = new Participant(
                                        snapShot.getString("role"),
                                        snapShot.getString("locationType"),
                                        snapShot.getString("status"),
                                        snapShot.getString("locationName"),
                                        snapShot.getString("ParticipantId"),
                                        snapShot.getString("email"),
                                        Double.parseDouble(Objects.requireNonNull(snapShot.getString("distanceFromCurrentLocation"))),
                                        snapShot.getString("userList"),
                                        Integer.parseInt(Objects.requireNonNull(snapShot.getString("maxCapacity"))),
                                        Integer.parseInt(Objects.requireNonNull(snapShot.getString("totalVolunteers"))),
                                        Integer.parseInt(Objects.requireNonNull(snapShot.getString("totalTestedVolunteers"))),
                                        Double.parseDouble(Objects.requireNonNull(snapShot.getString("lat"))),
                                        Double.parseDouble(Objects.requireNonNull(snapShot.getString("lng"))),
                                        snapShot.getString("leader")
                                );
                                list.add(participant);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            System.out.println("Error getting documents: " + task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "You have not registered any sites. Please come back later!", Toast.LENGTH_LONG).show();
                    }
                });
        ;
    }

    // Get participant role (identify that participant is leader or volunteer)
    public static void getRole(Context context, FirebaseFirestore db, List<Participant> list, User user) {
        String email = user.getEmail();
        CollectionReference participantRef = db.collection("Participant");
        participantRef.whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot snapShot : task.getResult()) {
                                Participant participant = new Participant(
                                        snapShot.getString("role"),
                                        snapShot.getString("locationType"),
                                        snapShot.getString("status"),
                                        snapShot.getString("locationName"),
                                        snapShot.getString("ParticipantId"),
                                        snapShot.getString("email"),
                                        Double.parseDouble(Objects.requireNonNull(snapShot.getString("distanceFromCurrentLocation"))),
                                        snapShot.getString("userList"),
                                        Integer.parseInt(Objects.requireNonNull(snapShot.getString("maxCapacity"))),
                                        Integer.parseInt(Objects.requireNonNull(snapShot.getString("totalVolunteers"))),
                                        Integer.parseInt(Objects.requireNonNull(snapShot.getString("totalTestedVolunteers"))),
                                        Double.parseDouble(Objects.requireNonNull(snapShot.getString("lat"))),
                                        Double.parseDouble(Objects.requireNonNull(snapShot.getString("lng"))),
                                        snapShot.getString("leader")
                                );
                                list.add(participant);
                            }
                        } else {
                            System.out.println("Error getting documents: " + task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "You have not registered any sites. Please come back later!", Toast.LENGTH_LONG).show();
                    }
                });
        ;
    }

}


