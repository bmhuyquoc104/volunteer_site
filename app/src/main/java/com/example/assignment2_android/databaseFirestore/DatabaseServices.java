package com.example.assignment2_android.databaseFirestore;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class DatabaseServices {

    public void registerUser(String userName, Context context, String password, String correctPassWord, ProgressDialog progressDialog,FirebaseFirestore db) {
        if (!userName.isEmpty() && !password.isEmpty() && !correctPassWord.isEmpty()) {
            if (password.equals(correctPassWord)) {
                String id = UUID.randomUUID().toString();
                progressDialog.show();
                HashMap<String, Object> temp = new HashMap<>();
                temp.put("id", id);
                temp.put("userName", userName);
                temp.put("password", password);
                db.collection("Volunteers").document(id).set(temp)

                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        progressDialog.dismiss();
                                        Toast.makeText(context,"You have successfully add!",Toast.LENGTH_LONG).show();
                                    }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(context,"Please try again!!",Toast.LENGTH_LONG).show();
                            }
                        });
            }else{
                Toast.makeText(context,"Please fill all fields ",Toast.LENGTH_LONG).show();
            }
        }

    }
}
