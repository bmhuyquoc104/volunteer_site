package com.example.assignment2_android.model;

import androidx.annotation.NonNull;

public class Participant {
    private User user;
    private String role;
    private VolunteerSite volunteerSite;
    private String locationType;
    private String status;
    private String locationName;
    private String email;
    private String id;
    private double distanceFromCurrentLocation;
    private String userList;
    public Participant(){

    }



    public Participant(User user, String role, VolunteerSite volunteerSite) {
        this.user = user;
        this.role = role;
        this.volunteerSite = volunteerSite;
    }

    public Participant (String locationType, String status, String role,String locationName,String email,String id,double distanceFromCurrentLocation,String userList){
        this.distanceFromCurrentLocation = distanceFromCurrentLocation;
        this.locationType = locationType;
        this.status = status;
        this.role = role;
        this.locationName = locationName;
        this.email = email;
        this.id = id;
        this.userList = userList;
    };

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getUserList() {
        return userList;
    }

    public void setUserList(String userList) {
        this.userList = userList;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getDistanceFromCurrentLocation() {
        return distanceFromCurrentLocation;
    }

    public void setDistanceFromCurrentLocation(double distanceFromCurrentLocation) {
        this.distanceFromCurrentLocation = distanceFromCurrentLocation;
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
                ", locationType='" + locationType + '\'' +
                ", status='" + status + '\'' +
                ", locationName='" + locationName + '\'' +
                ", email='" + email + '\'' +
                ", id='" + id + '\'' +
                ", distanceFromCurrentLocation=" + distanceFromCurrentLocation +
                ", userList='" + userList + '\'' +
                '}';
    }
}
