package com.example.assignment2_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2_android.R;
import com.example.assignment2_android.model.Volunteer;

import java.util.List;

public class VolunteerListAdapter extends RecyclerView.Adapter<VolunteerListAdapter.MyViewHolder> {
    private List<Volunteer> volunteerList;
    private Context context;

    public VolunteerListAdapter(List<Volunteer>volunteerList,Context context) {
        this.volunteerList = volunteerList;
        this.context = context;
    }


    @NonNull
    @Override
    public VolunteerListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.one_volunteer,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VolunteerListAdapter.MyViewHolder holder, int position) {
        holder.name.setText(volunteerList.get(position).getName());
        holder.age.setText(volunteerList.get(position).getAge());
        holder.email.setText(volunteerList.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return volunteerList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, age;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.volunter_name);
            email = itemView.findViewById(R.id.volunteer_email);
            age = itemView.findViewById(R.id.volunteer_age);
        }
    }
}
