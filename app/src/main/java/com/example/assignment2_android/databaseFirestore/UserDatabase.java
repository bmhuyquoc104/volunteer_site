package com.example.assignment2_android.databaseFirestore;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.assignment2_android.adapter.VolunteerListAdapter;
import com.example.assignment2_android.model.User;
import com.example.assignment2_android.model.VolunteerSite;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserDatabase {
    // Post User to database using Firestore
    public static void registerUser(@NonNull String userName, String email, String age, Context context, String password, String correctPassWord,
                             ProgressDialog progressDialog, FirebaseFirestore db) {
        if (!userName.isEmpty() && !password.isEmpty() && !correctPassWord.isEmpty()) {
            if (password.equals(correctPassWord)) {
                String id = UUID.randomUUID().toString();
                progressDialog.setTitle("Registering new account !");
                progressDialog.show();
                HashMap<String, Object> temp = new HashMap<>();
                temp.put("age",age);
                temp.put("email",email);
                temp.put("id", id);
                temp.put("userName", userName);
                temp.put("password", password);
                db.collection("Users").document(id).set(temp)

                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(context, "You have successfully registered!", Toast.LENGTH_LONG).show();
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

    public static void fetchVolunteers(Context context, FirebaseFirestore db, ProgressDialog pd,
                                List<User> list, VolunteerListAdapter adapter) {
        db.collection("Users").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                list.clear();
                for (DocumentSnapshot snapShot : task.getResult()) {
                    User user = new User(
                            snapShot.getString("userName"),
                            snapShot.getString("password"),
                            snapShot.getString("email"),
                            Integer.parseInt(Objects.requireNonNull(snapShot.getString("age"))),
                            snapShot.getString("id")
                    );
                    list.add(user);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public static void getAllUsers(FirebaseFirestore db, List<User>list){
        db.collection("Users").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for (DocumentSnapshot snapShot : task.getResult()) {
                            User user = new User(
                                    snapShot.getString("userName"),
                                    snapShot.getString("password"),
                                    snapShot.getString("email"),
                                    Integer.parseInt(Objects.requireNonNull(snapShot.getString("age"))),
                                    snapShot.getString("id")
                            );
                            list.add(user);

                        }
                    }
                });
    }

    // Check password and email from user to return the correct user
    public static void getUserByUserNameAndPassword (Context context, FirebaseFirestore db,
                                              String password, String username,List<User>list,ProgressDialog progressDialog){
        progressDialog.setTitle("Logging!");
        progressDialog.show();
        CollectionReference userRef = db.collection("Users");
        userRef.whereEqualTo("userName",username)
                .whereEqualTo("password",password)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            for (QueryDocumentSnapshot snapShot: task.getResult()){
                                User user = new User(
                                  snapShot.getString("userName"),
                                  snapShot.getString("password"),
                                  snapShot.getString("email"),
                                  Integer.parseInt(Objects.requireNonNull(snapShot.getString("age"))),
                                  snapShot.getString("id")
                                );
                                list.add(user);
                            }

                        }else{
                            System.out.println("Error getting documents: " + task.getException());
                        }
                }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Wrong username or password. Please try again!", Toast.LENGTH_LONG).show();
                    }
                });;
    }


    // Return the user from their email (email is unique)
    public static void getUserByEmail (Context context, FirebaseFirestore db,
                                              String email, List<User>list){
        CollectionReference userRef = db.collection("Users");
        userRef.whereEqualTo("email",email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot snapShot: task.getResult()){
                                User user = new User(
                                        snapShot.getString("userName"),
                                        snapShot.getString("password"),
                                        snapShot.getString("email"),
                                        Integer.parseInt(Objects.requireNonNull(snapShot.getString("age"))),
                                        snapShot.getString("id")
                                );
                                list.add(user);
                            }

                        }else{
                            System.out.println("Error getting documents: " + task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Wrong username or password. Please try again!", Toast.LENGTH_LONG).show();
                    }
                });;
    }


    // Generate list of users and then post to firestore
    public static void postToUsers(FirebaseFirestore db, ArrayList<User> userList, Context context) {
        for (User user :
                userList) {
            String id = user.getId();
            int age = user.getAge();
            String email = user.getEmail();
            String username = user.getName();
            String password = user.getPassword();

            HashMap<String, Object> temp = new HashMap<>();
            temp.put("age", Integer.toString(age));
            temp.put("email",email);
            temp.put("id", id);
            temp.put("userName", username);
            temp.put("password", password);
            db.collection("Users").document(id).set(temp)

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

