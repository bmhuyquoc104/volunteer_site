package com.example.assignment2_android.model;

public class Location {
    private String locationId;
    private String locationName;
    private String leaderName;
    private String maxCapacity;
    private String totalVolunteers;
    private String testedDate;
    private double lat;
    private double lng;

    public Location (String locationId, String locationName, String leaderName,
                     String maxCapacity, String totalVolunteers, String testedDate,
                     double lat, double lng) {

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

    public String getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(String maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public String getTotalVolunteers() {
        return totalVolunteers;
    }

    public void setTotalVolunteers(String totalVolunteers) {
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

}
