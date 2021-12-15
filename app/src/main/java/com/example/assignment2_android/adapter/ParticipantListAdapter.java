package com.example.assignment2_android.adapter;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2_android.R;
import com.example.assignment2_android.SeeMoreDetails;
import com.example.assignment2_android.UpdateSiteBySuperUser;
import com.example.assignment2_android.model.Participant;
import com.example.assignment2_android.model.User;

import java.util.List;

public class ParticipantListAdapter extends RecyclerView.Adapter<ParticipantListAdapter.MyViewHolder> {
    private List<Participant> participantList;
    private Context context;

    public ParticipantListAdapter(List<Participant> participantList, Context context) {
        this.participantList = participantList;
        this.context = context;
    }

    @NonNull
    @Override
    public ParticipantListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.one_participant,parent,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ParticipantListAdapter.MyViewHolder holder, int position) {
        holder.role.setText("Role: " + participantList.get(position).getRole());
        holder.locationName.setText("Site Name: " + participantList.get(position).getLocationName());
        holder.leader.setText("Email: " + participantList.get(position).getLeader());
        holder.seeMore.setOnClickListener(view ->{
            Intent intent = new Intent(context, SeeMoreDetails.class);
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("plain/text");
            intent.putExtra("siteLeader", participantList.get(position).getLeader());
            intent.putExtra("siteLat", Double.toString(participantList.get(position).getLat()));
            intent.putExtra("siteLng", Double.toString(participantList.get(position).getLng()));
            intent.putExtra("siteName", participantList.get(position).getLocationName());
            intent.putExtra("siteCapacity", Integer.toString(participantList.get(position).getMaxCapacity()));
            intent.putExtra("siteVolunteers", Integer.toString(participantList.get(position).getTotalVolunteers()));
            intent.putExtra("siteTestedNumber", Integer.toString(participantList.get(position).getTotalTestedVolunteers()));
            intent.putExtra("siteStatus", participantList.get(position).getStatus());
            intent.putExtra("siteType", participantList.get(position).getLocationType());
            intent.putExtra("siteDistance",Double.toString(participantList.get(position).getDistanceFromCurrentLocation()));
            intent.putExtra("siteListOfUsers", participantList.get(position).getUserList());
            intent.putExtra("siteRole", participantList.get(position).getRole());
            try {
                context.startActivity(intent);
            }
            catch (ActivityNotFoundException e){
                Toast.makeText(context,"Oops!! Something wrong, Please try again!" ,Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return participantList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView role, locationName, type,status,userList,leader;
        Button seeMore;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            role = itemView.findViewById(R.id.participant_role);
            locationName = itemView.findViewById(R.id.participant_site);
            leader = itemView.findViewById(R.id.participant_leader);
            seeMore = itemView.findViewById(R.id.seeMoreButton);
        }
    }
}
