package com.example.assignment2_android.listDisplay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.assignment2_android.R;
import com.example.assignment2_android.adapter.VolunteerListAdapter;
import com.example.assignment2_android.databaseFirestore.UserDatabase;
import com.example.assignment2_android.model.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class UserList extends AppCompatActivity {
    private List<User> userList;
    private FirebaseFirestore db;
    private UserDatabase userDatabase;
    private ProgressDialog pd;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter volunteerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private VolunteerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        userList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        userDatabase = new UserDatabase();
        pd = new ProgressDialog(this);
        // Set fixed size for recycler view
        recyclerView = findViewById(R.id.userList);
        recyclerView.setHasFixedSize(true);

        //Use a linear layout manager
        layoutManager = new LinearLayoutManager(UserList.this);
        recyclerView.setLayoutManager(layoutManager);

        // Specify an adapter
        adapter = new VolunteerListAdapter(userList,this);
        recyclerView.setAdapter(adapter);

//        userDatabase.fetchVolunteers(this,db,pd,userList,adapter);
    }


}