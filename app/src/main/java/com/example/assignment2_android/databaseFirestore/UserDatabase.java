package com.example.assignment2_android.databaseFirestore;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.assignment2_android.adapter.VolunteerListAdapter;
import com.example.assignment2_android.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserDatabase {
    // Post User to database using Firestore
//    public void registerUser(@NonNull String userName, String email, String age, Context context, String password, String correctPassWord,
//                             ProgressDialog progressDialog, FirebaseFirestore db) {
//        if (!userName.isEmpty() && !password.isEmpty() && !correctPassWord.isEmpty()) {
//            if (password.equals(correctPassWord)) {
//                String id = UUID.randomUUID().toString();
//                progressDialog.show();
//                HashMap<String, Object> temp = new HashMap<>();
//                temp.put("age",age);
//                temp.put("email",email);
//                temp.put("id", id);
//                temp.put("userName", userName);
//                temp.put("password", password);
//                db.collection("Users").document(id).set(temp)
//
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//                                    progressDialog.dismiss();
//                                    Toast.makeText(context, "You have successfully add!", Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                progressDialog.dismiss();
//                                Toast.makeText(context, "Please try again!!", Toast.LENGTH_LONG).show();
//                            }
//                        });
//            } else {
//                Toast.makeText(context, "Please fill all fields ", Toast.LENGTH_LONG).show();
//            }
//        }
//
//    }

    public void fetchVolunteers(Context context, FirebaseFirestore db, ProgressDialog pd,
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
                    System.out.println("list neeeeeeeee" + list);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

//    public static void getAllUsers(FirebaseFirestore db, List<User>list){
//        db.collection("Users").get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        list.clear();
//                        for (DocumentSnapshot snapShot : task.getResult()) {
//                            User user = new User(
//                                    snapShot.getString("userName"),
//                                    snapShot.getString("password"),
//                                    snapShot.getString("email"),
//                                    Integer.parseInt(Objects.requireNonNull(snapShot.getString("age"))),
//                                    snapShot.getString("id")
//                            );
//                            list.add(user);
//                        }
//                    }
//                });
//        System.out.println(list);
//    }

    public void getUserByUserNameAndPassword (Context context, FirebaseFirestore db,
                                              String password, String username,List<User>list){
        CollectionReference userRef = db.collection("Users");
        userRef.whereEqualTo("userName",username)
                .whereEqualTo("password",password)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
}

