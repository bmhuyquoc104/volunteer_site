package com.example.assignment2_android.model;

import static com.example.assignment2_android.MainActivity.allUsers;

import android.location.Address;
import android.location.Geocoder;

import androidx.annotation.NonNull;

import com.example.assignment2_android.MainActivity;
import com.example.assignment2_android.site.RandomLocation;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.UUID;

//Model volunteer site
public class VolunteerSite {
    private RandomLocation randomLocation;
    private String locationId;
    private String locationType;
    private String locationName;
    private String leader;
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
    private List<String> randomEmail = new ArrayList<>();
    public static List<String> allMails = MainActivity.userEmails;
    List<User> totalUsers= new ArrayList<>();
    public VolunteerSite(){

    }

    public VolunteerSite(double lat, double lng){
        this.lat = lat;
        this.lng =lng;
    }


    public VolunteerSite(String locationId, String locationName, String leader,String status,
                         int maxCapacity, int totalVolunteers, String locationType,
                         double lat, double lng, double distanceFromCurrentLocation,int totalTestedVolunteers,String userList ) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.leader = leader;
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



    //Generate location (dont need to manual type) easy to customize the size, and value
    public void generateNewLocation(LatLng originalLocation, int totalSite, int radius,
                                    ArrayList<VolunteerSite>volunteerSiteList,
                                    RandomLocation randomLocation,List<String>userMail){
        // Random base on the totalSite
            for(int i= 1; i < totalSite ; i++) {
                double[] result = randomLocation.getRandomLocation
                        (originalLocation.longitude, originalLocation.latitude, radius);
                double lat1 = result[0];
                double lng1 = result[1];
                String locationId = UUID.randomUUID().toString();
                String leader = getRandomLeader(userMail);
                int maxCapacity = getRandomMaximumCapacity(randomMaxCapacity);
                int totalVolunteers = getRandomTotalNumber(randomTotalNumber, maxCapacity);
                System.out.println("totalVolunteers" + totalVolunteers);
                String locationType = getRandomType(randomType);
                String locationName = locationType + "_"  +maxCapacity + "_" + i;
                String status = checkStatus(maxCapacity,totalVolunteers);
                double distanceFromCurrentLocation = distance(originalLocation.latitude, originalLocation.longitude, lat1, lng1);
                String userEmailList = getRandomEmail(totalVolunteers,userMail);
                int totalTestedVolunteers = getRandomTestedVolunteer(randomTestedVolunteer, totalVolunteers);
                VolunteerSite temp = new VolunteerSite(locationId,locationName,leader,status,
                        maxCapacity,totalVolunteers,locationType,lat1,lng1,distanceFromCurrentLocation
                ,totalTestedVolunteers,userEmailList);
                volunteerSiteList.add(temp);
            }
    }


    // Function add new Site
    public VolunteerSite addNewLocation(LatLng originalLocation,
                                        String leaderName,String locationType, String userList,int totalVolunteers,
                                        int maxCapacity,double lat,double lng){
        String locationId = UUID.randomUUID().toString();
        String locationName = leaderName + "_" + locationType;
        String status = checkStatus(maxCapacity,totalVolunteers);
        // when create new site, testedNumber always = 0
        int totalTestedVolunteers = 0;
        // Use distance function to calculate distance from current location
        double distanceFromCurrentLocation = distance(originalLocation.latitude, originalLocation.longitude, lat, lng);
        return new VolunteerSite(locationId,locationName,leaderName,status,maxCapacity,totalVolunteers,locationType,lat,lng,distanceFromCurrentLocation,totalTestedVolunteers,userList);
    }


    // Random leader base on the list of user
    // leader = User email
    public String getRandomLeader(List<String> list)
    {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    // Get random email without repetitions and put to the user list in sites
    public static String getRandomEmail(int totalNumber,List<String>email){
        String userEmails = "";
        String temp = "";
        System.out.println("hello ne nha" + email + "" + email.size());
        List<String> newList = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < totalNumber; i++){
            int randomIndex = rand.nextInt(email.size());

            // add element in temporary list
            newList.add(email.get(randomIndex));

            email.remove(randomIndex);

            StringBuilder sb = new StringBuilder();
            for (String eachEmail:newList
            ) {
                sb.append(eachEmail);
//                if (!eachEmail.equals(newList.get(newList.size() - 1)))
                    sb.append(",");
            }
            temp = sb.toString();
            userEmails = temp.substring(0,temp.length()-1);
        }
        return userEmails;
    };


    // Random type
    public static String getRandomType(List<String> list){
        list.add("School");
        list.add("Hospital");
        list.add("Stadium");
        list.add("Factory");

        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    };

    // Calculate distance between 2 locations in km
    public double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = (dist * 60 * 1.1515)*1.609344;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    // Random capacity
    public  int getRandomMaximumCapacity(List<Integer> list)
    {
        for (int i = 5; i < 25; i++){
            list.add(i);
        }

        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    //Random total number but always < capacity
    public  int getRandomTotalNumber(List<Integer> list,int maxCapacity){
        for (int i = 4; i < 25; i++){
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

    //Random testedVolunteer but always < total Volunteers
    public static int getRandomTestedVolunteer(List<Integer> list,int totalNumber){
        for (int i = 2; i < 23; i++){
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

    // Check status by capacity and volunteers
    public String checkStatus(int maxCapacity, int totalVolunteers){
        if (totalVolunteers < maxCapacity){
            status = "available";
        }
        else{
            status = "full";
        }
        return status;
    }

    //Random name
    public static String getRandomLeaderName(List<String> list){
        list.add("chris");
        list.add("kate");
        list.add("stark");
        list.add("strange");
        list.add("stephen");
        list.add("messi");
        list.add("leo");
        list.add("david");
        list.add("huy");
        list.add("john");
        list.add("minh");
        list.add("faker");

        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    };

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

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
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
                ", leader=" + leader + '\n' +
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
