package com.example.assignment2_android.adapter;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2_android.R;
import com.example.assignment2_android.RunReport;
import com.example.assignment2_android.model.Participant;
import com.example.assignment2_android.model.VolunteerSite;

import java.util.List;

public class SiteListAdapter extends RecyclerView.Adapter<SiteListAdapter.MyViewHolder>  {
    private List<VolunteerSite> siteList;
    private Context context;

    public SiteListAdapter(List<VolunteerSite> siteList, Context context) {
        this.siteList = siteList;
        this.context = context;
    }

    @NonNull
    @Override
    public SiteListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.one_site,parent,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SiteListAdapter.MyViewHolder holder, int position) {
        holder.leaderName.setText("Leader Name: "+siteList.get(position).getLeaderName());
        holder.lat.setText("Latitude: "+siteList.get(position).getLat());
        holder.lng.setText("Longitude: " + siteList.get(position).getLng());
        holder.runButton.setOnClickListener(view ->{
           Intent intent = new Intent(context, RunReport.class);
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("plain/text");
            intent.putExtra("siteId", siteList.get(position).getLocationId());
            intent.putExtra("siteLeader", siteList.get(position).getLeaderName());
            intent.putExtra("siteLat", Double.toString(siteList.get(position).getLat()));
            intent.putExtra("siteLng", Double.toString(siteList.get(position).getLng()));
            intent.putExtra("siteName", siteList.get(position).getLocationName());
            intent.putExtra("siteCapacity", Integer.toString(siteList.get(position).getMaxCapacity()));
            intent.putExtra("siteVolunteers", Integer.toString(siteList.get(position).getTotalVolunteers()));
            intent.putExtra("siteTestedNumber", Integer.toString(siteList.get(position).getTotalTestedVolunteers()));
            intent.putExtra("siteStatus", siteList.get(position).getStatus());
            intent.putExtra("siteType", siteList.get(position).getLocationType());
            intent.putExtra("siteListOfUsers", siteList.get(position).getUserList());
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
        return siteList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView leaderName,lat,lng,runButton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            leaderName = itemView.findViewById(R.id.leaderSite);
            lat = itemView.findViewById(R.id.latSite);
            lng = itemView.findViewById(R.id.lngSite);
            runButton = itemView.findViewById(R.id.playButton);
        }
    }
}
