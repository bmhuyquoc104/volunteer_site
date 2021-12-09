package com.example.assignment2_android.model;

import androidx.annotation.NonNull;

public class VolunteerSite {
    private String locationId;
    private String locationName;
    private String leaderName;
    private int maxCapacity;
    private int totalVolunteers;
    private String testedDate;
    private double lat;
    private double lng;
    private double distanceFromCurrentLocation;

    public VolunteerSite(double lat, double lng){
        this.lat = lat;
        this.lng =lng;
    }


    public VolunteerSite(String locationId, String locationName, String leaderName,
                         int maxCapacity, int totalVolunteers, String testedDate,
                         double lat, double lng) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.leaderName = leaderName;
        this.maxCapacity = maxCapacity;
        this.totalVolunteers = totalVolunteers;
        this.testedDate = testedDate;

    };

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
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

    public String getTestedDate() {
        return testedDate;
    }

    public void setTestedDate(String testedDate) {
        this.testedDate = testedDate;
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

    @NonNull
    @Override
    public String toString() {
        return "Location{" +
                "locationId='" + locationId + '\'' +
                ", locationName='" + locationName + '\'' +
                ", leaderName='" + leaderName + '\'' +
                ", maxCapacity='" + maxCapacity + '\'' +
                ", totalVolunteers='" + totalVolunteers + '\'' +
                ", testedDate='" + testedDate + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", distanceFromCurrentLocation=" + distanceFromCurrentLocation +
                '}';
    }
}
