package com.example.assignment2_android.databaseFirestore;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.assignment2_android.adapter.VolunteerListAdapter;
import com.example.assignment2_android.model.Volunteer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class DatabaseServices {

    // Post User to database using Firestore
    public void registerUser(@NonNull String userName, String email, String age, Context context, String password, String correctPassWord,
                             ProgressDialog progressDialog, FirebaseFirestore db) {
        if (!userName.isEmpty() && !password.isEmpty() && !correctPassWord.isEmpty()) {
            if (password.equals(correctPassWord)) {
                String id = UUID.randomUUID().toString();
                progressDialog.show();
                HashMap<String, Object> temp = new HashMap<>();
                temp.put("age",age);
                temp.put("email",email);
                temp.put("id", id);
                temp.put("userName", userName);
                temp.put("password", password);
                db.collection("Volunteers").document(id).set(temp)

                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(context, "You have successfully add!", Toast.LENGTH_LONG).show();
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
            } else {
                Toast.makeText(context, "Please fill all fields ", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void fetchVolunteers(Context context, FirebaseFirestore db, ProgressDialog pd,
                                List<Volunteer> list, VolunteerListAdapter adapter) {
        db.collection("Volunteers").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                list.clear();
                for (DocumentSnapshot snapShot : task.getResult()) {
                    Volunteer volunteer = new Volunteer(
                            snapShot.getString("userName"),
                            snapShot.getString("password"),
                            snapShot.getString("email"),
                            snapShot.getString("age"),
                            snapShot.getString("id")
                    );
                    list.add(volunteer);
                    System.out.println("list neeeeeeeee" + list);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}

