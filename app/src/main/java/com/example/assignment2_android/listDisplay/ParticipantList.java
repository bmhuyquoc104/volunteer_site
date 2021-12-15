package com.example.assignment2_android.listDisplay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.assignment2_android.LogIn;
import com.example.assignment2_android.R;
import com.example.assignment2_android.SuperUserHome;
import com.example.assignment2_android.VolunteerHome;
import com.example.assignment2_android.adapter.ParticipantListAdapter;
import com.example.assignment2_android.adapter.VolunteerListAdapter;
import com.example.assignment2_android.databaseFirestore.ParticipantDatabase;
import com.example.assignment2_android.databaseFirestore.SiteLocationDatabase;
import com.example.assignment2_android.databaseFirestore.UserDatabase;
import com.example.assignment2_android.model.Participant;
import com.example.assignment2_android.model.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ParticipantList extends AppCompatActivity {
    private List<Participant> participantList;
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter participantAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ParticipantListAdapter adapter;
    List<User>currentUserList = new ArrayList<>();
    User currentUser = new User();
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_list);
        back = findViewById(R.id.back);
        participantList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();

        // Set fixed size for recycler view
        recyclerView = findViewById(R.id.participantList);
        recyclerView.setHasFixedSize(true);

        //Use a linear layout manager
        layoutManager = new LinearLayoutManager(ParticipantList.this);
        recyclerView.setLayoutManager(layoutManager);

        // Specify an adapter
        adapter = new ParticipantListAdapter(participantList,this);
        recyclerView.setAdapter(adapter);
        currentUserList = LogIn.oneUserlist;

        for (User thisUser:currentUserList
        ) {
            currentUser = new User(thisUser.getName(),thisUser.getPassword(),thisUser.getEmail(), thisUser.getAge(),thisUser.getId());
        }


        ParticipantDatabase.fetchParticipant(this,db,participantList,currentUser,adapter);
        back.setOnClickListener( view ->{
            Intent intent2 = new Intent(this, VolunteerHome.class);
            // Delete all stacks before to avoid stack memory redundant and collapse between stacks
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent2);
        });
    }
}