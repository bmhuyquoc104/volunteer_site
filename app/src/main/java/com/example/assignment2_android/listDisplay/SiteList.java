package com.example.assignment2_android.listDisplay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.assignment2_android.MainActivity;
import com.example.assignment2_android.R;
import com.example.assignment2_android.SuperUserHome;
import com.example.assignment2_android.UserGuide;
import com.example.assignment2_android.VolunteerHome;
import com.example.assignment2_android.adapter.SiteListAdapter;
import com.example.assignment2_android.adapter.VolunteerListAdapter;
import com.example.assignment2_android.databaseFirestore.UserDatabase;
import com.example.assignment2_android.model.User;
import com.example.assignment2_android.model.VolunteerSite;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SiteList extends AppCompatActivity {

    private List<VolunteerSite> siteList;
    private FirebaseFirestore db;
    private UserDatabase userDatabase;
    private ProgressDialog pd;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter volunteerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SiteListAdapter adapter;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_list);
        db = FirebaseFirestore.getInstance();
        userDatabase = new UserDatabase();
        back = findViewById(R.id.back);
        siteList = MainActivity.allSites;
        pd = new ProgressDialog(this);
        // Set fixed size for recycler view
        recyclerView = findViewById(R.id.siteList);
        recyclerView.setHasFixedSize(true);

        //Use a linear layout manager
        layoutManager = new LinearLayoutManager(SiteList.this);
        recyclerView.setLayoutManager(layoutManager);

        // Specify an adapter
        adapter = new SiteListAdapter(siteList,this);
        recyclerView.setAdapter(adapter);

        back.setOnClickListener( view ->{
            Intent intent2 = new Intent(this, SuperUserHome.class);
            // Delete all stacks before to avoid stack memory redundant and collapse between stacks
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent2);
        });
//        userDatabase.fetchVolunteers(this,db,pd,userList,adapter);
    }
}