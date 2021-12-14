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
    public static void addParticipant(Participant participant, FirebaseFirestore db,
                                      Context context) {
        String listOfUsers = "";
        String id = UUID.randomUUID().toString();
        String role = participant.getRole();
        if (role.equals("leader")) {
            listOfUsers = participant.getVolunteerSite().getUserList();
        }

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
        String userList = participant.getVolunteerSite().getUserList();
        int maxCapacity = participant.getVolunteerSite().getMaxCapacity();
        int totalVolunteers = participant.getVolunteerSite().getTotalVolunteers();
        double lat = participant.getVolunteerSite().getLat();
        double lng = participant.getVolunteerSite().getLng();
        String distanceFromCurrentLocation = Double.toString(participant.getVolunteerSite().getDistanceFromCurrentLocation());
        int totalTestedVolunteers = participant.getVolunteerSite().getTotalTestedVolunteers();


        HashMap<String, Object> temp = new HashMap<>();
        temp.put("locationName", locationName);
        temp.put("distanceFromCurrentLocation", distanceFromCurrentLocation);
        temp.put("status", status);
        temp.put("locationType", locationType);
        temp.put("role", role);
        temp.put("email", email);
        temp.put("userList", listOfUsers);
        temp.put("ParticipantId", id);

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


    public static void addParticipantByEmail(String email, FirebaseFirestore db,
                                      Context context,VolunteerSite site,String role) {
        String id = UUID.randomUUID().toString();
        String locationName = site.getLocationName();
        String locationType = site.getLocationType();
        String status = site.getStatus();
        String userList = site.getUserList();
        String distanceFromCurrentLocation = Double.toString(site.getDistanceFromCurrentLocation());




        HashMap<String, Object> temp = new HashMap<>();
        temp.put("locationName", locationName);
        temp.put("distanceFromCurrentLocation", distanceFromCurrentLocation);
        temp.put("status", status);
        temp.put("locationType", locationType);
        temp.put("role", role);
        temp.put("email", email);
        temp.put("userList", userList);
        temp.put("ParticipantId", id);


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



    public static void fetchParticipant(Context context, FirebaseFirestore db, List<Participant> list, User user, ParticipantListAdapter adapter) {
        String email = user.getEmail();
        System.out.println("email ne" + email);
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
                                        snapShot.getString("locationType"),
                                        snapShot.getString("status"),
                                        snapShot.getString("role"),
                                        snapShot.getString("locationName"),
                                        snapShot.getString("email"),
                                        snapShot.getString("ParticipantId"),
                                        Double.parseDouble(Objects.requireNonNull(snapShot.getString("distanceFromCurrentLocation"))),
                                        snapShot.getString("userList")
                                );
                                list.add(participant);
                                System.out.println("listne!!!!!!!!!!" + list);
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

    public static void getRole(Context context, FirebaseFirestore db, List<Participant> list, User user) {
        String email = user.getEmail();
        System.out.println("email ne" + email);
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
                                        snapShot.getString("locationType"),
                                        snapShot.getString("status"),
                                        snapShot.getString("role"),
                                        snapShot.getString("locationName"),
                                        snapShot.getString("email"),
                                        snapShot.getString("ParticipantId"),
                                        Double.parseDouble(Objects.requireNonNull(snapShot.getString("distanceFromCurrentLocation"))),
                                        snapShot.getString("userList"));
                                list.add(participant);
                                System.out.println("listne!!!!!!!!!!" + list);
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


