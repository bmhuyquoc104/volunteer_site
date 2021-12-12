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
        holder.role.setText(participantList.get(position).getRole());
        holder.locationName.setText(participantList.get(position).getLocationName());
        holder.type.setText(participantList.get(position).getLocationType());
        holder.status.setText(participantList.get(position).getStatus());
        holder.distanceFromCurrentLocation.setText(Double.toString(participantList.get(position).getDistanceFromCurrentLocation()));
    }

    @Override
    public int getItemCount() {
        return participantList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView role, locationName, type,status,distanceFromCurrentLocation;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            role = itemView.findViewById(R.id.user_role);
            locationName = itemView.findViewById(R.id.siteName);
            type = itemView.findViewById(R.id.siteType);
            status = itemView.findViewById(R.id.siteStatus);
            distanceFromCurrentLocation = itemView.findViewById(R.id.siteTotalDistance);
        }
    }
}
