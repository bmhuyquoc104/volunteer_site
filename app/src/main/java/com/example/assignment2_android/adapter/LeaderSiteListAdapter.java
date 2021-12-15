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
import com.example.assignment2_android.UpdateSiteBySuperUser;
import com.example.assignment2_android.model.Participant;
import com.example.assignment2_android.model.VolunteerSite;

import java.util.List;

public class LeaderSiteListAdapter extends RecyclerView.Adapter<LeaderSiteListAdapter.MyViewHolder>{
    private List<VolunteerSite> leaderSiteList;
    private Context context;

    public LeaderSiteListAdapter(List<VolunteerSite> leaderSiteList, Context context) {
        this.leaderSiteList = leaderSiteList;
        this.context = context;
    }

    @NonNull
    @Override
    public LeaderSiteListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.one_leader_site,parent,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LeaderSiteListAdapter.MyViewHolder holder, int position) {
        holder.siteName.setText("Site Name: " + leaderSiteList.get(position).getLocationName());
        // Send intent base on position and on click for each item
        holder.itemView.setOnClickListener(view ->{
            Intent intent = new Intent(context, UpdateSiteBySuperUser.class);
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("plain/text");
            intent.putExtra("siteId", leaderSiteList.get(position).getLocationId());
            intent.putExtra("siteLeader", leaderSiteList.get(position).getLeader());
            intent.putExtra("siteLat", Double.toString(leaderSiteList.get(position).getLat()));
            intent.putExtra("siteLng", Double.toString(leaderSiteList.get(position).getLng()));
            intent.putExtra("siteName", leaderSiteList.get(position).getLocationName());
            intent.putExtra("siteCapacity", Integer.toString(leaderSiteList.get(position).getMaxCapacity()));
            intent.putExtra("siteVolunteers", Integer.toString(leaderSiteList.get(position).getTotalVolunteers()));
            intent.putExtra("siteTestedNumber", Integer.toString(leaderSiteList.get(position).getTotalTestedVolunteers()));
            intent.putExtra("siteStatus", leaderSiteList.get(position).getStatus());
            intent.putExtra("siteType", leaderSiteList.get(position).getLocationType());
            intent.putExtra("siteDistance",Double.toString(leaderSiteList.get(position).getDistanceFromCurrentLocation()));
            intent.putExtra("siteListOfUsers", leaderSiteList.get(position).getUserList());
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
        return leaderSiteList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView siteName,num;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            siteName = itemView.findViewById(R.id.leaderSiteName);
            num = itemView.findViewById(R.id.siteNum);
        }
    }
}
