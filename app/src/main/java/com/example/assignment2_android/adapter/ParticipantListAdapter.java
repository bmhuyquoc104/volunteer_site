package com.example.assignment2_android.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2_android.R;
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
        holder.role.setText("Role: "+participantList.get(position).getRole());
        holder.locationName.setText("Site Name: "+participantList.get(position).getLocationName());
        holder.type.setText("Type: " + participantList.get(position).getLocationType());
        holder.status.setText("Status: " +participantList.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return participantList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView role, locationName, type,status;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            role = itemView.findViewById(R.id.participant_role);
            locationName = itemView.findViewById(R.id.participant_site);
            type = itemView.findViewById(R.id.participant_type);
            status = itemView.findViewById(R.id.participant_status);

        }
    }
}
