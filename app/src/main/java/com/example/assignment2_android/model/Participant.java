package com.example.assignment2_android.model;

import androidx.annotation.NonNull;

// Participant model
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
    private int maxCapacity;
    private int totalVolunteers;
    private int totalTestedVolunteers;
    private double lat;
    private double lng;
    private String leader;
    public Participant(){

    }

    // For get participant by role
    public Participant(User user, String role, VolunteerSite volunteerSite) {
        this.user = user;
        this.role = role;
        this.volunteerSite = volunteerSite;
    }

    // For get all participant infos to display
    public Participant(String role, String locationType, String status, String locationName,String id, String email, double distanceFromCurrentLocation, String userList, int maxCapacity, int totalVolunteers, int totalTestedVolunteers, double lat, double lng, String leader) {
        this.role = role;
        this.locationType = locationType;
        this.status = status;
        this.locationName = locationName;
        this.email = email;
        this.distanceFromCurrentLocation = distanceFromCurrentLocation;
        this.userList = userList;
        this.id = id;
        this.maxCapacity = maxCapacity;
        this.totalVolunteers = totalVolunteers;
        this.totalTestedVolunteers = totalTestedVolunteers;
        this.lat = lat;
        this.lng = lng;
        this.leader = leader;
    }

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

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getTotalVolunteers() {
        return totalVolunteers;
    }

    public void setTotalVolunteers(int totalVolunteers) {
        this.totalVolunteers = totalVolunteers;
    }

    public int getTotalTestedVolunteers() {
        return totalTestedVolunteers;
    }

    public void setTotalTestedVolunteers(int totalTestedVolunteers) {
        this.totalTestedVolunteers = totalTestedVolunteers;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
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
