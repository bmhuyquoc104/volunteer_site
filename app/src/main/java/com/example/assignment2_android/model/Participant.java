package com.example.assignment2_android.model;

import androidx.annotation.NonNull;

public class Participant {
    private User user;
    private String role;
    private VolunteerSite volunteerSite;

    public Participant(){

    }

    public Participant(User user, String role, VolunteerSite volunteerSite) {
        this.user = user;
        this.role = role;
        this.volunteerSite = volunteerSite;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public VolunteerSite getVolunteerSite() {
        return volunteerSite;
    }

    public void setVolunteerSite(VolunteerSite volunteerSite) {
        this.volunteerSite = volunteerSite;
    }



    @NonNull
    @Override
    public String toString() {
        return "Participant{" +
                "user=" + user +
                ", role='" + role + '\'' +
                ", volunteerSite=" + volunteerSite +
                '}';
    }
}
