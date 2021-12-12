package com.example.assignment2_android.listDisplay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.assignment2_android.LogIn;
import com.example.assignment2_android.R;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_list);

        participantList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();

        // Set fixed size for recycler view
        recyclerView = findViewById(R.id.siteList);
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

        System.out.println("huyne!!!!!!!!" + currentUser);

        ParticipantDatabase.fetchParticipant(this,db,participantList,currentUser,adapter);
        System.out.println("huy nua ne !!!!!!" +participantList);
    }
}