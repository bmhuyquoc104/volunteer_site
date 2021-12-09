package com.example.assignment2_android.leader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.assignment2_android.R;
import com.example.assignment2_android.adapter.VolunteerListAdapter;
import com.example.assignment2_android.databaseFirestore.DatabaseServices;
import com.example.assignment2_android.model.Volunteer;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class VolunteerList extends AppCompatActivity {
    private List<Volunteer> volunteerList ;
    private FirebaseFirestore db;
    private DatabaseServices databaseServices;
    private ProgressDialog pd;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter volunteerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private VolunteerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        volunteerList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        databaseServices = new DatabaseServices();
        pd = new ProgressDialog(this);
        // Set fixed size for recycler view
        recyclerView = findViewById(R.id.volunteerList);
        recyclerView.setHasFixedSize(true);

        //Use a linear layout manager
        layoutManager = new LinearLayoutManager(VolunteerList.this);
        recyclerView.setLayoutManager(layoutManager);

        // Specify an adapter
        adapter = new VolunteerListAdapter(volunteerList,this);
        recyclerView.setAdapter(adapter);

        databaseServices.fetchVolunteers(this,db,pd,volunteerList,adapter);
    }


}