package com.example.assignment2_android.model;

import android.location.Address;
import android.location.Geocoder;

import androidx.annotation.NonNull;

import com.example.assignment2_android.site.RandomLocation;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class VolunteerSite {
    private RandomLocation randomLocation;
    private String locationId;
    private String locationType;
    private String locationName;
    private String leaderName;
    private int maxCapacity;
    private String status;
    private int totalVolunteers;
    private int totalTestedVolunteers;
    private double lat;
    private double lng;
    private double distanceFromCurrentLocation;
    private String userList;
    private final LatLng HOCHIMINH = new LatLng(10.762622,106.660172);
    private final LatLng HANOI = new LatLng(21.0245,106.660172);
    private final LatLng DALAT = new LatLng(11.936230,108.445259);
    private List<Integer> randomMaxCapacity = new ArrayList<>();
    private List<Integer> randomTotalNumber = new ArrayList<>();
    private List<Integer> randomTestedVolunteer = new ArrayList<>();
    private List<String> randomType = new ArrayList<>();
    private List<String> randomLeaderName = new ArrayList<>();
    public VolunteerSite(){

    }

    public VolunteerSite(double lat, double lng){
        this.lat = lat;
        this.lng =lng;
    }


    public VolunteerSite(String locationId, String locationName, String leaderName,String status,
                         int maxCapacity, int totalVolunteers, String locationType,
                         double lat, double lng, double distanceFromCurrentLocation,int totalTestedVolunteers,String userList ) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.leaderName = leaderName;
        this.maxCapacity = maxCapacity;
        this.totalVolunteers = totalVolunteers;
        this.locationType = locationType;
        this.lat = lat;
        this.lng = lng;
        this.distanceFromCurrentLocation = distanceFromCurrentLocation;
        this.totalTestedVolunteers = totalTestedVolunteers;
        this.status = status;
        this.userList = userList;
    };



    public void generateNewLocation(LatLng originalLocation, int totalSite, int radius,
                                    ArrayList<VolunteerSite>volunteerSiteList,
                                    RandomLocation randomLocation){
            for(int i= 0; i < totalSite ; i++) {
                double[] result = randomLocation.getRandomLocation
                        (originalLocation.longitude, originalLocation.latitude, radius);
                double lat1 = result[0];
                System.out.println(lat1);
                double lng1 = result[1];
                System.out.println(lng1);
                String locationId = UUID.randomUUID().toString();
                String leaderName = getRandomLeaderName(randomLeaderName);
                int maxCapacity = getRandomMaximumCapacity(randomMaxCapacity);
                int totalVolunteers = getRandomTotalNumber(randomTotalNumber, maxCapacity);
                String locationType = getRandomType(randomType);
                String locationName = leaderName + "_" + locationType + i;
                String status = checkStatus(maxCapacity,totalVolunteers);
                double distanceFromCurrentLocation = distance(originalLocation.latitude, originalLocation.longitude, lat1, lng1);
                String userList = "huy,an,khang";
                int totalTestedVolunteers = getRandomTestedVolunteer(randomTestedVolunteer, totalVolunteers);
                VolunteerSite temp = new VolunteerSite(locationId,locationName,leaderName,status,
                        maxCapacity,totalVolunteers,locationType,lat1,lng1,distanceFromCurrentLocation
                ,totalTestedVolunteers,userList);
                volunteerSiteList.add(temp);
            }
    }

    public static String getRandomType(List<String> list){
        list.add("secondary-school");
        list.add("hospital");
        list.add("highSchool");
        list.add("factory");
        list.add("supermarket");

        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    };

    public static String getRandomLeaderName(List<String> list){
        list.add("huy");
        list.add("andrew");
        list.add("ngoc");
        list.add("khang");
        list.add("steve");
        list.add("thu");
        list.add("dat");
        list.add("anh");

        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    };

    private double distance(double lat1, double lng1, double lat2, double lng2) {
        double theta = lng1 - lng2;
        double dist = Math.sin((lat1 * Math.PI / 180.0))
                * Math.sin((lat2 * Math.PI / 180.0))
                + Math.cos((lat1 * Math.PI / 180.0))
                * Math.cos((lat2 * Math.PI / 180.0))
                * Math.cos((lat2 * Math.PI / 180.0));
        dist = Math.acos(dist);
        dist = (dist * 180.0 / Math.PI);
        dist = dist * 60 * 1.1515 *1.609344;
        return (dist);
    }

    public  int getRandomMaximumCapacity(List<Integer> list)
    {
        for (int i = 35; i < 50; i++){
            list.add(i);
        }

        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    public  int getRandomTotalNumber(List<Integer> list,int maxCapacity){
        for (int i = 25; i < 50; i++){
            list.add(i);
        }

        Random rand = new Random();
        int temp = list.get(rand.nextInt(list.size()));
        if (temp >= maxCapacity){
            return maxCapacity;
        }
        else{
            return temp;
        }

    };

    public static int getRandomTestedVolunteer(List<Integer> list,int totalNumber){
        for (int i = 10; i < 50; i++){
            list.add(i);
        };

        Random rand = new Random();
        int temp = list.get(rand.nextInt(list.size()));
        if (temp >= totalNumber){
            return totalNumber;
        }
        else{
            return temp;
        }

    };

    private String checkStatus(int maxCapacity, int totalVolunteers){
        if (totalVolunteers < maxCapacity){
            status = "available";
        }
        else{
            status = "full";
        }
        return status;
    }


    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public double getDistanceFromCurrentLocation() {
        return distanceFromCurrentLocation;
    }

    public void setDistanceFromCurrentLocation(double distanceFromCurrentLocation) {
        this.distanceFromCurrentLocation = distanceFromCurrentLocation;
    }

    public String getUserList() {
        return userList;
    }

    public void setUserList(String userList) {
        this.userList = userList;
    }

    public LatLng getHOCHIMINH() {
        return HOCHIMINH;
    }

    public LatLng getHANOI() {
        return HANOI;
    }

    public LatLng getDALAT() {
        return DALAT;
    }

    @NonNull
    @Override
    public String toString() {
        return "VolunteerSite{" + '\n' +
                "locationId=" + locationId + '\n' +
                ", locationType=" + locationType + '\n' +
                ", locationName=" + locationName + '\n' +
                ", leaderName=" + leaderName + '\n' +
                ", maxCapacity=" + maxCapacity + '\n' +
                ", status=" + status + '\n' +
                ", totalVolunteers=" + totalVolunteers + '\n' +
                ", totalTestedVolunteers=" + totalTestedVolunteers + '\n' +
                ", lat=" + lat + '\n' +
                ", lng=" + lng + '\n' +
                ", distanceFromCurrentLocation=" + distanceFromCurrentLocation + " km" + '\n' +
                ", userList=" + userList + '\n' +
                '}';
    }
}
