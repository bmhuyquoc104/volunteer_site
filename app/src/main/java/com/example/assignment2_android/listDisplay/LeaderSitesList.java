package com.example.assignment2_android.listDisplay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.assignment2_android.R;
import com.example.assignment2_android.SuperUserHome;
import com.example.assignment2_android.UserGuide;
import com.example.assignment2_android.VolunteerHome;
import com.example.assignment2_android.adapter.LeaderSiteListAdapter;
import com.example.assignment2_android.adapter.SiteListAdapter;
import com.example.assignment2_android.databaseFirestore.UserDatabase;
import com.example.assignment2_android.model.VolunteerSite;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class LeaderSitesList extends AppCompatActivity {
    private List<VolunteerSite> leaderSitesList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter leaderAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private LeaderSiteListAdapter adapter;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_sites_list);
        back = findViewById(R.id.back);
        leaderSitesList = VolunteerHome.leaderSites;

        for (VolunteerSite site : leaderSitesList) {
            System.out.println("hello" +site);
        }
        // Set fixed size for recycler view
        recyclerView = findViewById(R.id.siteList);
        recyclerView.setHasFixedSize(true);

        //Use a linear layout manager
        layoutManager = new LinearLayoutManager(LeaderSitesList.this);
        recyclerView.setLayoutManager(layoutManager);

        // Specify an adapter
        adapter = new LeaderSiteListAdapter(leaderSitesList,this);
        recyclerView.setAdapter(adapter);

        back.setOnClickListener( view ->{
            Intent intent2 = new Intent(this, VolunteerHome.class);
            // Delete all stacks before to avoid stack memory redundant and collapse between stacks
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent2);
        });
    }
}